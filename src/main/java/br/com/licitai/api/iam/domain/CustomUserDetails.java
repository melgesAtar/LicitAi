package br.com.licitai.api.iam.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private final String userId;
    private final String username;
    private final String accountId;
    private final Set<Role> roles;
    private final String password;
    private final boolean enabled;

    public CustomUserDetails(String userId, String username, String accountId, Set<Role> roles, String password, boolean enabled) {
        this.userId = userId;
        this.username = username;
        this.accountId = accountId;
        this.roles = roles;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public Set<Role> getUserRoles() {
        return roles;
    }

    public AuthenticatedUser toAuthenticatedUser() {
        return new AuthenticatedUser(userId, accountId, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

