package br.com.licitai.iam.application.command;

import br.com.licitai.iam.domain.*;


import java.util.UUID;


public record RegisterUserCommand(
        AuthenticatedUser authenticatedUser,
        String username,
        Email email,
        RawPassword rawPassword,
        Role role,
        UUID accountId,
        UserStatus userStatus
) {

}