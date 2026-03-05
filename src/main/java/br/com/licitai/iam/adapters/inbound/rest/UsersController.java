package br.com.licitai.iam.adapters.inbound.rest;


import br.com.licitai.iam.adapters.inbound.rest.dto.RegisterUserRequest;
import br.com.licitai.iam.application.RegisterUserUseCase;
import br.com.licitai.iam.application.command.RegisterUserCommand;
import br.com.licitai.iam.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final RegisterUserUseCase registerUserUseCase;

    public UsersController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping
    public ResponseEntity<?> register(
            @RequestBody RegisterUserRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        // Converter UserDetails para AuthenticatedUser (domínio)
        AuthenticatedUser authenticatedUser = userDetails.toAuthenticatedUser();

        // Construir command
        RegisterUserCommand command = new RegisterUserCommand(
                authenticatedUser,
                request.username(),
                Email.of(request.email()),
                RawPassword.of(request.password()),
                request.role() != null ? Role.valueOf(request.role()) : Role.USER,
                request.accountId(),
                UserStatus.ACTIVE
        );

        // Executar use case
        User createdUser = registerUserUseCase.execute(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

}
