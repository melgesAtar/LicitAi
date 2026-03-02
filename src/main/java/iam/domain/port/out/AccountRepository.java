package iam.domain.port.out;

import iam.domain.Account;
import java.util.UUID;

public interface AccountRepository {
        Account findById(UUID accountId);
        Account findByName(String name);
        Boolean existsById(UUID accountId);
        Account save(Account account);

}
