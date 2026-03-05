package br.com.licitai.iam.domain.port;

import java.util.UUID;

/**
 * Context que carrega o accountId atual da requisição (tenant atual).
 * Usado para garantir isolamento de dados entre tenants.
 */
public interface TenantContext {

    /**
     * Retorna o accountId do tenant atual da thread/requisição
     */
    UUID getCurrentAccountId();

    /**
     * Define o accountId do tenant para a thread/requisição atual
     */
    void setCurrentAccountId(UUID accountId);

    /**
     * Limpa o contexto do tenant
     */
    void clear();

    /**
     * Verifica se há um tenant definido no contexto
     */
    boolean hasTenantContext();
}

