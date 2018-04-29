package com.learning.library.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.learning.library.model.Category;

public class CategoryRepository {
    private EntityManager em;

    @Inject
    public CategoryRepository(final EntityManager em) {
        this.em = em;
    }

    public Category add(final Category category) {
        em.persist(category);
        return category;
    }

    public boolean alreadyExists(final Category category) {
        final StringBuilder jpql = new StringBuilder("Select 1 From Category e Where e.name = :name");

        if (category.getId() != null) {
            jpql.append(" And e.id != :id");
        }

        final Query query = em.createQuery(jpql.toString());
        query.setParameter("name", category.getName());

        if (category.getId() != null) {
            query.setParameter("id", category.getId());
        }

        return query.setMaxResults(1).getResultList().size() > 0;
    }

    public boolean alreadyExists(final Long categoryId) {
        final Query query = em.createQuery("select 1 from Category e where e.id = :id");
        query.setParameter("id", categoryId);

        return query.setMaxResults(1).getResultList().size() > 0;
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAll(final String orderField) {
        final Query query = em.createQuery("Select e From Category e Order by e." + orderField);
        return query.getResultList();
    }

    public Category findById(final Long id) {
        if (id == null) {
            return null;
        }
        return em.find(Category.class, id);
    }

    public void update(final Category category) {
        em.merge(category);
    }
}
