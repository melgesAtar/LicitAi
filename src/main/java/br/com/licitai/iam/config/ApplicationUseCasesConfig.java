package br.com.licitai.iam.config;

import br.com.licitai.iam.application.RegisterUserUseCase;
import br.com.licitai.iam.domain.port.TenantContext;
import br.com.licitai.iam.domain.port.out.AccountRepository;
import br.com.licitai.iam.domain.port.out.EmailSender;
import br.com.licitai.iam.domain.port.out.PasswordEncryptor;
import br.com.licitai.iam.domain.port.out.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationUseCasesConfig {

    @Bean public RegisterUserUseCase registerUserUseCase(
            PasswordEncryptor passwordEncryptor,
            UserRepository userRepository,
            AccountRepository accountRepository,
            EmailSender emailSender,
            TenantContext tenantContext) {
        return new RegisterUserUseCase(
                passwordEncryptor,
                userRepository,
                accountRepository,
                emailSender,
                tenantContext );
    }
}
