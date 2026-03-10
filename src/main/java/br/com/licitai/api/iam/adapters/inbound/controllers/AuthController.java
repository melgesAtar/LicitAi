package br.com.licitai.api.iam.adapters.inbound.controllers;

import br.com.licitai.api.iam.adapters.inbound.controllers.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok("Login endpoint");
    }
}