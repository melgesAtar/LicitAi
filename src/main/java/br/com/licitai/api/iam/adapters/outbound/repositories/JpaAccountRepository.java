package br.com.licitai.api.iam.adapters.outbound.repositories;

import br.com.licitai.api.iam.adapters.outbound.entities.JpaAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaAccountRepository extends JpaRepository<JpaAccountEntity, UUID> {


}
