/**
 *
 */
package com.learning.library.repository;

import static com.learning.library.repository.data.CategoryData.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.learning.library.model.Category;
import com.learning.library.repository.common.DBCommandTransactionalExecutor;

/**
 * @author Dell
 *
 */
public class CategoryRepositoryTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    private CategoryRepository categoryRepository;

    private DBCommandTransactionalExecutor commandExecutor;

    @Test
    public void addCategory() {
        final Long categoryId = commandExecutor.executeCommand(() -> categoryRepository.addCategory(java()).getId());
        assertNotNull(categoryId);

        final Category category = categoryRepository.findById(categoryId);
        assertNotNull(category);
        assertEquals(java().getName(), category.getName());
    }

    @Before
    public void setup() {
        emf = Persistence.createEntityManagerFactory("library-persistence-unit");
        em = emf.createEntityManager();
        categoryRepository = new CategoryRepository(em);
        commandExecutor = new DBCommandTransactionalExecutor(em);
    }

    @After
    public void teardown() {
        em.close();
        emf.close();
    }

}
