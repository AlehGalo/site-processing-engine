/**
 * 
 */
package com.jdev.domain.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import org.springframework.util.Assert;

import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh
 * 
 * @param <T>
 */
class CommonGenericReadDao<T extends IIdentifiable> extends AbstractGenericDao<T> implements
        IReadAllDao<T> {

    /**
     * @param clazz
     *            Generic class.
     */
    public CommonGenericReadDao(final Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T get(final Long id) {
        if (id == null) {
            return null;
        }
        return getEntityManager().find(getPersistentClass(), id);
    }

    @Override
    public long countAll() {
        //
        //
        // final CriteriaQuery<Long> query =
        // getCriteriaComposer().createCriteriaQuery(Long.class);
        // return getCriteriaComposer().createTypedQuery(
        // query.select(getCriteriaComposer().getCriteriaBuilder().count(
        // query.from(getPersistentClass())))).getSingleResult();
        //
        //
        //
        CriteriaQuery<Long> criteriaQuery = criteriaComposer.createCriteriaQuery(Long.class);
        return count(criteriaQuery.select(criteriaComposer.count(criteriaQuery)));
    }

    @Override
    public List<T> find(final Expression<Boolean> restriction) {
        return criteriaComposer.createTypedQuery(
                criteriaComposer.createCriteriaQueryWithRestriction(restriction,
                        criteriaComposer.createCriteriaQuery())).getResultList();
    }

    @Override
    public List<T> find(final CriteriaQuery<T> query) {
        Assert.notNull(query);
        return criteriaComposer.createTypedQuery(query).getResultList();
    }

    @Override
    public List<T> findAll() {
        return find(criteriaComposer.getCriteriaBuilder().createQuery(getPersistentClass())
                .select(criteriaComposer.createRoot()));
    }

    @Override
    public long count(CriteriaQuery<Long> criteriaQuery) {
        return criteriaComposer.createTypedQuery(criteriaQuery).getSingleResult();
    }
}
