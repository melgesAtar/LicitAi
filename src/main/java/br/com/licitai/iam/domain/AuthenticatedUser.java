package br.com.licitai.iam.domain;

import java.util.Set;

public class AuthenticatedUser {
    private final String userId;
    private final String accountId;
    private final Set<Role> roles;

    public AuthenticatedUser(String userId, String accountId, Set<Role> roles) {
        this.userId = userId;
        this.accountId = accountId;
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}