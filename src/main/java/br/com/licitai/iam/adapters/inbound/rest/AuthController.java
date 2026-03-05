package br.com.licitai.iam.adapters.inbound.rest;

import br.com.licitai.iam.adapters.inbound.rest.dto.LoginRequest;
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