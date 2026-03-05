package iam.application;


import iam.domain.*;
import iam.application.command.RegisterUserCommand;
import java.util.UUID;

public class RegisterUserUseCase {

    PasswordHasher passwordHasher = new PasswordHasher();

    public User execute(RegisterUserCommand command) {



        // Encrypt the password
        // Create a new User entity
        // Save the user to the repository
        // Send a welcome email
        // Return the created user
        return null; // Placeholder for actual implementation
    }
}
