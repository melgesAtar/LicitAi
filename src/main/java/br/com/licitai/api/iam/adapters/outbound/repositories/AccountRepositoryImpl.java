package br.com.licitai.api.iam.adapters.outbound.repositories;


import br.com.licitai.api.iam.domain.account.Account;
import br.com.licitai.api.iam.domain.account.AccountRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryImpl(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public Account findById(String id) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
