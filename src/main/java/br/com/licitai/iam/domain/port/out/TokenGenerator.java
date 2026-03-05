package br.com.licitai.iam.domain.port.out;

public interface TokenGenerator {

    String generateToken(String userId, String username, String[] roles);
}
