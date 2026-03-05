package br.com.licitai.iam.application;


import br.com.licitai.iam.domain.Account;
import br.com.licitai.iam.domain.Password;
import br.com.licitai.iam.domain.Role;
import br.com.licitai.iam.domain.User;
import br.com.licitai.iam.application.command.RegisterUserCommand;
import br.com.licitai.iam.domain.port.TenantContext;
import br.com.licitai.iam.domain.port.out.AccountRepository;
import br.com.licitai.iam.domain.port.out.EmailSender;
import br.com.licitai.iam.domain.port.out.PasswordEncryptor;
import br.com.licitai.iam.domain.port.out.UserRepository;
import br.com.licitai.iam.exceptions.UserAlreadyExistsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RegisterUserUseCase {

    private final PasswordEncryptor passwordEncryptor;
    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final EmailSender emailSender;
    private final TenantContext tenantContext;
    private static final Logger log = LoggerFactory.getLogger(RegisterUserUseCase.class);

    public RegisterUserUseCase(
            PasswordEncryptor passwordEncryptor,
            UserRepository repo,
            AccountRepository accountRepo,
            EmailSender emailSender,
            TenantContext tenantContext) {
        this.passwordEncryptor = passwordEncryptor;
        this.userRepo = repo;
        this.accountRepo = accountRepo;
        this.emailSender = emailSender;
        this.tenantContext = tenantContext;
    }


    public User execute(RegisterUserCommand command) {

        // ========== VALIDAÇÕES MULTI-TENANT ==========

        // 1. Validar se há contexto de tenant (segurança)
        if (!tenantContext.hasTenantContext()) {
            throw new IllegalStateException("Contexto de tenant não encontrado");
        }

        // 2. Obter o accountId do tenant atual (da requisição)
        var currentAccountId = tenantContext.getCurrentAccountId();

        // 3. CRITICAL: Validar que o usuário autenticado pertence à mesma conta
        if (!command.authenticatedUser().getAccountId().equals(currentAccountId.toString())) {
            throw new SecurityException("Usuário autenticado não pertence ao tenant atual");
        }

        // 4. CRITICAL: Validar que está tentando criar usuário na própria conta
        if (!command.accountId().equals(currentAccountId)) {
            throw new SecurityException("Não é permitido criar usuário em outra conta (tenant isolation violation)");
        }

        // 5. Validar permissões (apenas ADMIN pode criar usuários)
        if (!command.authenticatedUser().getRoles().contains(Role.ADMIN)) {
            throw new IllegalArgumentException("Só administradores podem registrar novos usuários");
        }

        // ========== VALIDAÇÕES DE NEGÓCIO ==========

        // 6. Validar se a conta existe (com isolamento por tenant)
        Account account = accountRepo.findById(command.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        // 7. Validar limite de usuários do plano
        if (account.getPlan().getMaxUsers() <= accountRepo.countUsersInAccount(command.accountId())) {
            throw new IllegalStateException("Limite de usuários atingido para o plano atual da conta");
        }

        // 8. Validar email único DENTRO da conta (multi-tenant aware)
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
