package br.com.licitai.api.iam.domain.account;

public interface AccountRepository {

    Account save(Account account);
    Account findById(String id);
    void deleteById(String id);
}
