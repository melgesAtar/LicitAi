package br.com.licitai.iam.adapters.inbound.security;

import br.com.licitai.iam.domain.CustomUserDetails;
import br.com.licitai.iam.domain.port.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filtro que popula o TenantContext baseado no usuário autenticado.
 * DEVE rodar APÓS o JwtAuthenticationFilter.
 */
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    private final TenantContext tenantContext;

    public TenantContextFilter(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
                UUID accountId = UUID.fromString(userDetails.getAccountId());
                tenantContext.setCurrentAccountId(accountId);
            }

            filterChain.doFilter(request, response);
        } finally {
            // IMPORTANTE: limpar o contexto após a requisição
            tenantContext.clear();
        }
    }
}

