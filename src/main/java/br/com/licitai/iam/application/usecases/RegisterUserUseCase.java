package br.com.licitai.iam.application.usecases;


import br.com.licitai.iam.domain.Account;
import br.com.licitai.iam.domain.Password;
import br.com.licitai.iam.domain.Role;
import br.com.licitai.iam.domain.User;
import br.com.licitai.iam.application.command.RegisterUserCommand;
import br.com.licitai.iam.domain.port.out.AccountRepository;
import br.com.licitai.iam.domain.port.out.EmailSender;
import br.com.licitai.iam.domain.port.out.PasswordEncryptor;
import br.com.licitai.iam.domain.port.out.UserRepository;
import br.com.licitai.iam.infra.exceptions.UserAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class RegisterUserUseCase {

    private final PasswordEncryptor passwordEncryptor;
    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final EmailSender emailSender;
    private static final Logger log = LoggerFactory.getLogger(RegisterUserUseCase.class);

    public RegisterUserUseCase(
            PasswordEncryptor passwordEncryptor,
            UserRepository repo,
            AccountRepository accountRepo,
            EmailSender emailSender) {
        this.passwordEncryptor = passwordEncryptor;
        this.userRepo = repo;
        this.accountRepo = accountRepo;
        this.emailSender = emailSender;
    }


    public User execute(RegisterUserCommand command) {

        // 1. Validar permissões (apenas ADMIN pode criar usuários)
        if (!command.authenticatedUser().getRoles().contains(Role.ADMIN)) {
            throw new IllegalArgumentException("Só administradores podem registrar novos usuários");
        }

        // 2. Validar que está tentando criar usuário na própria conta
        var authenticatedAccountId = command.authenticatedUser().getAccountId();
        if (!command.accountId().toString().equals(authenticatedAccountId)) {
            throw new SecurityException("Não é permitido criar usuário em outra conta");
        }

        // 3. Validar se a conta existe
        Account account = accountRepo.findById(command.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        // 4. Validar limite de usuários do plano
        if (account.getPlan().getMaxUsers() <= accountRepo.countUsersInAccount(command.accountId())) {
            throw new IllegalStateException("Limite de usuários atingido para o plano atual da conta");
        }

        // 5. Validar email único dentro da conta
        if (userRepo.existsByEmail(command.email(), command.accountId())) {
            throw new UserAlreadyExistsException(command.email().getValue());
        }

        // ========== CRIAÇÃO DO USUÁRIO ==========

        Role role = command.role() != null ? command.role() : Role.USER;

        String hash = passwordEncryptor.encode(command.rawPassword());
        Password passwordHash = Password.fromHash(hash);

        User user = User.register(command.username(), command.email(), passwordHash, command.accountId(), role);

        User saved = userRepo.save(user);

        if (saved == null) throw new IllegalStateException("UserRepository.save returned null");

        // ========== EMAIL DE BOAS-VINDAS ==========

        try {
            emailSender.sendEmail(
                    saved.getEmail().getValue(),
                    "Bem-vindo ao LicitAI",
                    "Acesse: https://licitai.com.br (use seu email e a senha cadastrada)."
            );
        } catch (Exception ignored) {
            log.error("Falha ao enviar email de boas-vindas para {}", saved.getEmail().getValue(), ignored);
        }

        return saved;
    }

}
