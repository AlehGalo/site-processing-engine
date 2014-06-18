/**
 * 
 */
package com.jdev.domain.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.jdev.domain.dao.criteria.CriteriaComposer;
import com.jdev.domain.dao.criteria.ICriteriaComposer;
import com.jdev.domain.entity.IIdentifiable;

/**
 * @author Aleh
 * @param <T>
 *            Bean name. Common dao class.
 */
@Repository
abstract class AbstractGenericDao<T extends IIdentifiable> {

    /**
     * Entity manager.
     */
    private EntityManager entityManager;

    /**
     * Generic type class.
     */
    private final Class<T> clazz;

    /**
     * Composer.
     */
    protected ICriteriaComposer<T> criteriaComposer;

    /**
     * @param clazz
     *            Generic class.
     */
    public AbstractGenericDao(final Class<T> clazz) {
        Assert.notNull(clazz);
        this.clazz = clazz;
    }

    /**
     * @param entityManager
     *            the entityManager to set
     */
    @PersistenceContext(unitName = "informerPersistence", type = PersistenceContextType.TRANSACTION)
    protected final void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaComposer = new CriteriaComposer<>(this.entityManager, getPersistentClass());
    }

    /**
     * @return persistent class from generic
     */
    public Class<T> getPersistentClass() {
        return clazz;
    }

    /**
     * @return the criteriaComposer
     */
    public final ICriteriaComposer<T> getCriteriaComposer() {
        return criteriaComposer;
    }

    /**
     * @param criteriaComposer
     *            the criteriaComposer to set
     */
    public final void setCriteriaComposer(final ICriteriaComposer<T> criteriaComposer) {
        this.criteriaComposer = criteriaComposer;
    }

    /**
     * @return the entityManager
     */
    protected final EntityManager getEntityManager() {
        return entityManager;
    }

}
