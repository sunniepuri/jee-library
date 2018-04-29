/**
 *
 */
package com.learning.library.repository;

import static com.learning.library.repository.data.CategoryData.*;
import static org.junit.Assert.*;

import java.util.List;

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
        final Long categoryId = commandExecutor.executeCommand(() -> categoryRepository.add(java()).getId());
        assertNotNull(categoryId);

        final Category category = categoryRepository.findById(categoryId);
        assertNotNull(category);
        assertEquals(java().getName(), category.getName());
    }

    @Test
    public void alreadyExists() {
        final Category category = commandExecutor.executeCommand(() -> {
            categoryRepository.add(java());
            return categoryRepository.add(kotlin());
        });

        // Think it from update perspective. no updates to itself.
        assertFalse(categoryRepository.alreadyExists(category));

        // updates to entity results in this entity to be same as some other entity in db.
        category.setName(java().getName());
        assertTrue(categoryRepository.alreadyExists(category));

        // updates to entity results in this entity to be new entity which is not conflicting with any existing in db.
        category.setName(cleanCode().getName());
        assertFalse(categoryRepository.alreadyExists(category));
    }

    @Test
    public void existsById() {
        final Long categoryId = commandExecutor.executeCommand(() -> categoryRepository.add(java()).getId());
        assertNotNull(categoryId);

        assertTrue(categoryRepository.alreadyExists(categoryId));
        assertFalse(categoryRepository.alreadyExists(-1L));
    }

    @Test
    public void findAllCategories() {
        // Add Categories
        commandExecutor.executeCommand(() -> {
            allCategories().forEach(categoryRepository::add);
            return null;
        });

        final List<Category> categories = categoryRepository.findAll("name");
        assertEquals(allCategories().size(), categories.size());

        // Assert order of result
        assertEquals(architecture().getName(), categories.get(0).getName());
        assertEquals(cleanCode().getName(), categories.get(1).getName());
        assertEquals(java().getName(), categories.get(2).getName());
        assertEquals(kotlin().getName(), categories.get(3).getName());
    }

    @Test
    public void findCategoryByIdNotFound() {
        final Category category = categoryRepository.findById(-1L);
        assertNull(category);
    }

    @Test
    public void findCategoryByIdNull() {
        final Category category = categoryRepository.findById(null);
        assertNull(category);
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

    @Test
    public void updateCategory() {
        // Add
        final Long categoryId = commandExecutor.executeCommand(() -> categoryRepository.add(java()).getId());
        assertNotNull(categoryId);

        final Category categoryBeforeUpdate = categoryRepository.findById(categoryId);
        assertNotNull(categoryBeforeUpdate);
        assertEquals(java().getName(), categoryBeforeUpdate.getName());

        // Update
        categoryBeforeUpdate.setName(kotlin().getName());
        commandExecutor.executeCommand(() -> {
            categoryRepository.update(categoryBeforeUpdate);
            return null;
        });

        final Category categoryAfterUpdate = categoryRepository.findById(categoryId);
        assertNotNull(categoryAfterUpdate);
        assertEquals(kotlin().getName(), categoryAfterUpdate.getName());
    }
}
