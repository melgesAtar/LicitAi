package iam.domain.port.out;

import iam.domain.Password;
import iam.domain.RawPassword;

public interface PasswordEncryptor {

    String encode(RawPassword rawPassword);
    boolean matches(String rawPassword, Password encodedPassword);

}
