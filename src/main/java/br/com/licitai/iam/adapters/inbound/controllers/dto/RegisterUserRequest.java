package br.com.licitai.iam.adapters.inbound.controllers.dto;

import java.util.UUID;

public record RegisterUserRequest(
        String username,
        String email,
        String password,
        String role,
        UUID accountId
) {
}
