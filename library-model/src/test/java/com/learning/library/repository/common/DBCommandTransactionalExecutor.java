package com.learning.library.repository.common;

import javax.persistence.EntityManager;

import org.junit.Ignore;

@Ignore
public class DBCommandTransactionalExecutor {
    private EntityManager em;

    public DBCommandTransactionalExecutor(final EntityManager em) {
        this.em = em;
    }

    public <T> T executeCommand(final DBCommand<T> dbCommand) {
        try {
            em.getTransaction().begin();
            final T result = dbCommand.execute();
            em.getTransaction().commit();
            em.clear();
            return result;
        } catch (final Exception e) {
            em.getTransaction().rollback();
            throw new IllegalStateException("Failed to execute  the command, Reason: " + e.getMessage());
        }
    }
}
