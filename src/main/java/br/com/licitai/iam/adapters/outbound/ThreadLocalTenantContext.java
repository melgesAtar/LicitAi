package br.com.licitai.iam.adapters.outbound;

import br.com.licitai.iam.domain.port.TenantContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Implementação thread-safe do TenantContext usando ThreadLocal.
 * Cada thread (requisição) tem seu próprio accountId isolado.
 */
@Component
public class ThreadLocalTenantContext implements TenantContext {

    private static final ThreadLocal<UUID> currentAccountId = new ThreadLocal<>();

    @Override
    public UUID getCurrentAccountId() {
        UUID accountId = currentAccountId.get();
        if (accountId == null) {
            throw new IllegalStateException("No tenant context available. User must be authenticated.");
        }
        return accountId;
    }

    @Override
    public void setCurrentAccountId(UUID accountId) {
        if (accountId == null) {
            throw new IllegalArgumentException("AccountId cannot be null");
        }
        currentAccountId.set(accountId);
    }

    @Override
    public void clear() {
        currentAccountId.remove();
    }

    @Override
    public boolean hasTenantContext() {
        return currentAccountId.get() != null;
    }
}

