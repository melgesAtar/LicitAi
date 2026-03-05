package br.com.licitai.iam.domain.port.out;

import br.com.licitai.iam.domain.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
        Optional<Account> findById(UUID accountId);
        Optional<Account> findByName(String name);
        Boolean existsById(UUID accountId);
        Account save(Account account);
        int countUsersInAccount(UUID accountId);

}
