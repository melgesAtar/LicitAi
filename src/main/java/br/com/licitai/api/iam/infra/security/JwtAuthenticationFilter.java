package br.com.licitai.api.iam.infra.security;

import br.com.licitai.api.iam.domain.CustomUserDetails;
import br.com.licitai.api.iam.domain.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Filtro JWT para aplicações stateless.
 * Extrai o token, valida e popula o SecurityContext.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token = extractTokenFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUserId(token);
            String accountId = jwtTokenProvider.getAccountId(token);
            String rolesStr = jwtTokenProvider.getRoles(token);

            // Converter string de roles para Set<Role>
            Set<Role> roles = Arrays.stream(rolesStr.split(","))
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());

            // Criar UserDetails customizado
            CustomUserDetails userDetails = new CustomUserDetails(
                    userId,
                    userId, // username = userId (ou você pode extrair do token)
                    accountId,
                    roles,
                    "", // senha não é necessária aqui (stateless)
                    true // enabled
            );

            // Criar autenticação e popular SecurityContext
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // STATELESS: popula SecurityContext na memória, sem sessão
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token do cookie auth_token
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

