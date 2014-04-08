/**
 * 
 */
package com.jdev.domain.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.stereotype.Repository;

import com.jdev.domain.dao.criteria.CriteriaComposer;
import com.jdev.domain.dao.criteria.ICriteriaComposer;
import com.jdev.domain.domain.IIdentifiable;

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
     * Composer.
     */
    private ICriteriaComposer<T> criteriaComposer;

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
    public abstract Class<T> getPersistentClass();

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
