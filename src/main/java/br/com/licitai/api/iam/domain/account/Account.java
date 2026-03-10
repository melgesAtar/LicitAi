package br.com.licitai.api.iam.domain.account;

import br.com.licitai.api.iam.domain.Email;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Account {

    private UUID id;
    private String name;
    private Email email;
    private PlanType plan;
    private AccountStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Account(){}

    private Account(UUID id,
                    String name, Email email,
                    PlanType plan,
                    AccountStatus status,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {


        if (id == null) throw new IllegalArgumentException("id is required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
        if (plan == null) throw new IllegalArgumentException("plan is required");
        if (status == null) throw new IllegalArgumentException("status is required");
        if (createdAt == null) throw new IllegalArgumentException("createdAt is required");
        if (updatedAt == null) throw new IllegalArgumentException("updatedAt is required");
        if (email == null) throw new IllegalArgumentException("email is required");
        this.email = email;
        this.id = id;
        this.name = name.trim();
        this.plan = plan;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public UUID getId() { return id; }
    public String getName() { return name; }
    public PlanType getPlan() { return plan; }
    public AccountStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}