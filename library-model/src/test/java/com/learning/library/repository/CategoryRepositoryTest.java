/**
 *
 */
package com.learning.library.repository;

import static com.learning.library.repository.common.CategoryData.*;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.learning.library.model.Category;

/**
 * @author Dell
 *
 */
public class CategoryRepositoryTest {
    private EntityManagerFactory emf;
    private EntityManager em;

    private CategoryRepository categoryRepository;

    @Test
    public void addCategory() {
        Long categoryId = null;
        try {
            em.getTransaction().begin();
            categoryId = categoryRepository.addCategory(java()).getId();
            assertNotNull(categoryId);
            em.getTransaction().commit();
            em.clear();
        } catch (final Exception e) {
            em.getTransaction().rollback();
            fail("Exception while adding category");
        }
        final Category category = categoryRepository.findById(categoryId);
        assertNotNull(category);
        assertEquals(java().getName(), category.getName());
    }

    @Before
    public void setup() {
        emf = Persistence.createEntityManagerFactory("library-persistence-unit");
        em = emf.createEntityManager();
        categoryRepository = new CategoryRepository(em);
    }

    @After
    public void teardown() {
        em.close();
        emf.close();
    }

}
