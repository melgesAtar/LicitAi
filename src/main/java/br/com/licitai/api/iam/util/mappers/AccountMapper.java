package br.com.licitai.api.iam.util.mappers;

import br.com.licitai.api.iam.domain.account.Account;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface AccountMapper {

        @Mappings({
                @Mapping(source = "id", target = "id"),
                @Mapping(source = "email", target = "email"),
                @Mapping(source = "password.hash", target = "passwordHash"),
                @Mapping(source = "status", target = "status")
        })
        AccountDTO toDTO(Account account);

        @Mappings({
                @Mapping(source = "id", target = "id"),
                @Mapping(source = "email", target = "email"),
                @Mapping(source = "passwordHash", target = "password.hash"),
                @Mapping(source = "status", target = "status")
        })
        Account toEntity(AccountDTO accountDTO);

}
