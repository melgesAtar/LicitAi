package br.com.licitai.iam.adapters.outbound;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import br.com.licitai.iam.domain.Password;
import br.com.licitai.iam.domain.RawPassword;
import br.com.licitai.iam.domain.port.out.PasswordEncryptor;

public class ArgonPasswordEncryptor implements PasswordEncryptor {

    private Argon2 argon2;

    public ArgonPasswordEncryptor() {
        this.argon2 = Argon2Factory.create();
    }

    @Override
    public String encode(RawPassword rawPassword) {
        if (rawPassword.getValue() == null || rawPassword.getValue().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        return argon2.hash(2, 65536, 1, rawPassword.getValue());
    }

    @Override
    public boolean matches(String rawPassword, Password encodedPassword) {
        if (encodedPassword == null) {
            throw new IllegalArgumentException("Hash cannot be null or blank");
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        return argon2.verify(String.valueOf(encodedPassword), rawPassword);
    }
}
