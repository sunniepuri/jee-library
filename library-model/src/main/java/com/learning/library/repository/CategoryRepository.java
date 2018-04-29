package com.learning.library.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.learning.library.model.Category;

public class CategoryRepository {
    private EntityManager em;

    @Inject
    public CategoryRepository(final EntityManager em) {
        this.em = em;
    }

    public Category addCategory(final Category category) {
        em.persist(category);
        return category;
    }

    public Category findById(final Long id) {
        return em.find(Category.class, id);
    }
}
