package iam.domain;

import iam.domain.port.out.PasswordEncryptor;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.Set;


public final class User {
    private final UUID id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final Set<String> roles;

    public User(UUID id, String username, String email, String passwordHash, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.roles = roles;
    }

    public static User register(String username, String email, String password, PasswordEncryptor encryptor){
        Objects.requireNonNull(username);
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);
        String passwordHash = encryptor.encode(password);
        return new User(UUID.randomUUID(), username, email, passwordHash, new HashSet<>());
    }

    public boolean checkPassword(String passwordHash, PasswordEncryptor encryptor) {
        return encryptor.matches(passwordHash, this.passwordHash);
    }

    public User assignRole(String role){
        var newRole = new HashSet<>(this.roles);
        newRole.add(role);
        return new User(this.id, this.username, this.email, this.passwordHash, newRole);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
