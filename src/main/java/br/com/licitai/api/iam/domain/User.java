package br.com.licitai.api.iam.domain;



import java.util.HashSet;
import java.util.UUID;
import java.util.Set;


public final class User {
    private final UUID id;
    private final String username;
    private final Email email;
    private final Password passwordHash;
    private final Set<Role> roles;
    private final UUID accountId;
    private final UserStatus userStatus;



    private User(UUID id,
                 String username,
                 Email email,
                 Password passwordHash,
                 Set<Role> roles,
                 UUID accountId,
                 UserStatus userStatus) {

        if (id == null) throw new IllegalArgumentException("id is required");
        if (username == null || username.isBlank()) throw new IllegalArgumentException("username is required");
        if (email == null) throw new IllegalArgumentException("email is required");
        if (passwordHash == null) throw new IllegalArgumentException("passwordHash is required");
        if (accountId == null) throw new IllegalArgumentException("accountId is required");
        if (userStatus == null) throw new IllegalArgumentException("userStatus is required");
        if (roles == null || roles.isEmpty()) throw new IllegalArgumentException("User must have at least one role");

        this.id = id;
        this.username = username.trim();
        this.email = email;
        this.passwordHash = passwordHash;
        this.accountId = accountId;
        this.userStatus = userStatus;

        // evita alguém mexer no set por fora
        this.roles = Set.copyOf(roles);
    }

    public static User register(String username,
                                Email email,
                                Password passwordHash,
                                UUID accountId,
                                Role initialRole) {

        if (initialRole == null) throw new IllegalArgumentException("initialRole is required");

        return new User(
                UUID.randomUUID(),
                username,
                email,
                passwordHash,
                Set.of(initialRole),
                accountId,
                UserStatus.ACTIVE
        );
    }

    public User assignRole(Role role) {
        if (role == null) throw new IllegalArgumentException("role is required");

        Set<Role> newRoles = new HashSet<>(this.roles);
        newRoles.add(role);

        return new User(this.id, this.username, this.email, this.passwordHash, newRoles, this.accountId, this.userStatus);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public Password getPasswordHash() {
        return passwordHash;
    }
}
