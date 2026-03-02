package iam.domain.port.out;

public interface PasswordEncryptor {

    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);

}
