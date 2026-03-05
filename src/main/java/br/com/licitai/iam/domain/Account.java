package br.com.licitai.iam.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public final class Account {

    private final UUID id;
    private final String name;
    private PlanType plan;
    private AccountStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Account(UUID id,
                    String name,
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

        this.id = id;
        this.name = name.trim();
        this.plan = plan;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account create(String name, PlanType plan) {
        LocalDateTime now = LocalDateTime.now();
        return new Account(
                UUID.randomUUID(),
                name,
                plan,
                AccountStatus.ACTIVE,
                now,
                now
        );
    }

    public void changePlan(PlanType newPlan) {
        ensureNotInactive();
        if (newPlan == null) throw new IllegalArgumentException("newPlan is required");
        this.plan = newPlan;
        touch();
    }

    public void suspend() {
        ensureNotInactive();
        if (this.status == AccountStatus.SUSPENDED) return;
        this.status = AccountStatus.SUSPENDED;
        touch();
    }

    public void reactivate() {
        ensureNotInactive();
        if (this.status == AccountStatus.ACTIVE) return;
        this.status = AccountStatus.ACTIVE;
        touch();
    }

    public void deactivate() {
        if (this.status == AccountStatus.INACTIVE) return;
        this.status = AccountStatus.INACTIVE;
        touch();
    }

    private void ensureNotInactive() {
        if (this.status == AccountStatus.INACTIVE) {
            throw new IllegalStateException("Inactive account cannot be changed");
        }
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    // getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public PlanType getPlan() { return plan; }
    public AccountStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}