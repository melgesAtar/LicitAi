package br.com.licitai.iam.adapters.outbound.repositories;

import br.com.licitai.iam.domain.AccountStatus;
import br.com.licitai.iam.domain.PlanType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class JpaAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  UUID id;
    private  String name;
    private PlanType plan;
    private AccountStatus status;
    private  LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
