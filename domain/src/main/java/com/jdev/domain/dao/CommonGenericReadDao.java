/**
 * 
 */
package com.jdev.domain.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.util.Assert;

import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh
 * 
 * @param <T>
 */
class CommonGenericReadDao<T extends IIdentifiable> extends AbstractGenericDao<T> implements
        IReadDao<T> {

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
        CriteriaQuery<Long> criteriaQuery = criteriaComposer.createCriteriaQuery(Long.class);
        return count(criteriaQuery.select(criteriaComposer.getCriteriaBuilder().count(
                criteriaQuery.from(getPersistentClass()))));
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
        CriteriaQuery<T> criteriaQuery = criteriaComposer.createCriteriaQuery();
        return find(criteriaQuery.select(criteriaComposer.createRoot(criteriaQuery)));
    }

    @Override
    public long count(final CriteriaQuery<Long> criteriaQuery) {
        return criteriaComposer.createTypedQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public <X> List<X> find(final SingularAttribute<T, X> singularAttribute) {
        CriteriaQuery<X> criteriaQuery = criteriaComposer.getCriteriaBuilder().createQuery(
                singularAttribute.getBindableJavaType());
        Path<X> path = criteriaQuery.from(getPersistentClass()).get(singularAttribute);
        return getEntityManager()
                .createQuery(
                        criteriaQuery.select(path).orderBy(
                                criteriaComposer.getCriteriaBuilder().asc(path))).getResultList();
    }
}
