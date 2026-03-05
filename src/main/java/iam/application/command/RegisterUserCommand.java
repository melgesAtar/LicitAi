package iam.application.command;

Record RegisterUserCommand(
        String username,
        String email,
        String rawPassword,
        String role
) {
    public RegisterUserCommand {
        if(username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if(rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if(role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
    }
}