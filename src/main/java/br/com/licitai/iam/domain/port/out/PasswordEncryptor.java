package br.com.licitai.iam.domain.port.out;

import br.com.licitai.iam.domain.Password;
import br.com.licitai.iam.domain.RawPassword;

public interface PasswordEncryptor {

    String encode(RawPassword rawPassword);
    boolean matches(String rawPassword, Password encodedPassword);

}
