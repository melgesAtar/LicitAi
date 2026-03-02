package iam.application;


import iam.domain.*;

import java.util.UUID;

public class RegisterUserUseCase {

    public User execute(RegisterUserCommand command) {



        if(username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if(email == null || email.getValue().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if(rawPassword == null || rawPassword.getValue().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if(role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }


        // Encrypt the password
        // Create a new User entity
        // Save the user to the repository
        // Send a welcome email
        // Return the created user
        return null; // Placeholder for actual implementation
    }
}
