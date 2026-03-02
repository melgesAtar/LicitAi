package iam.domain;

public final class RawPassword {

    private final String value;

    public RawPassword(String value) {
        validate(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static void validate(String value) {

        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Password cannot be null or empty");

        if (value.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters long");

        if (!value.matches(".*[A-Z].*"))
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");

        if (!value.matches(".*[a-z].*"))
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");

        if (!value.matches(".*\\d.*"))
            throw new IllegalArgumentException("Password must contain at least one digit");

        if (!value.matches(".*[!@#$%^&*()].*"))
            throw new IllegalArgumentException("Password must contain at least one special character");
    }
}