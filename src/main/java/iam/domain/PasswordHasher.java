package iam.domain;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordHasher {
    private Argon2 argon2;

    public PasswordHasher() {
        this.argon2 = Argon2Factory.create();
    }

    public String hashPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        return argon2.hash(2, 65536, 1, password);
    }

    public boolean verifyPassword(String hash, String password) {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("Hash cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        return argon2.verify(hash, password);
    }
}
