package iam.domain.port.out;

public interface TokenGenerator {

    String generateToken(String userId, String username, String[] roles);
}
