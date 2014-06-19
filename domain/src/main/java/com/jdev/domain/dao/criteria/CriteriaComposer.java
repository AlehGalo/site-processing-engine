/**
 * 
 */
package com.jdev.domain.dao.criteria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author Aleh
 * @param <T>
 * 
 */
// TODO: startPosition, resultNumber
// TODO: startPosition, resultNumber, sortBy
public class CriteriaComposer<T> implements ICriteriaComposer<T> {

    /**
     * JPA entity manager.
     */
    private EntityManager entityManager;

    /**
     * Class for persistence entity.
     */
    private Class<T> persistanceClass;

    /**
     * Constructor.
     * 
     * @param manager
     * @param persistentClass
     */
    public CriteriaComposer(final EntityManager manager, final Class<T> persistentClass) {
        setManager(manager);
        setPersistanceClass(persistentClass);
    }

    /**
     * @return Criteria builder.
     */
    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return getManager().getCriteriaBuilder();
    }

    /**
     * @return Criteria query.
     */
    @Override
    public <E> CriteriaQuery<E> createCriteriaQuery(final Class<E> clazz) {
        return getCriteriaBuilder().createQuery(clazz);
    }

    /**
     * @return Criteria query.
     */
    @Override
    public CriteriaQuery<T> createCriteriaQuery() {
        return createCriteriaQuery(getPersistenceClass());
    }

    /**
     * @return Criteria query.
     */
    @Override
    public TypedQuery<T> createTypedQuery() {
        return getManager().createQuery(createCriteriaQuery());
    }

    /**
     * @return Criteria query.
     */
    @Override
    public <E> TypedQuery<E> createTypedQuery(final CriteriaQuery<E> query) {
        return getManager().createQuery(query);
    }

    /**
     * @param property
     *            any property.
     * @param value
     *            of the property.
     * @return Criteria query with property equality.
     */
    @Override
    public CriteriaQuery<T> createCriteriaQueryByStringProperty(final String property,
            final Object value) {
        final CriteriaQuery<T> criteriaQuery = createCriteriaQuery();
        return createCriteriaQueryWithRestriction(
                getCriteriaBuilder().equal(criteriaQuery.from(getPersistenceClass()).get(property),
                        value), criteriaQuery);
    }

    /**
     * @param property
     *            any property.
     * @param value
     *            of the property.
     * @return Criteria query with property equality.
     */
    @Override
    public <E> CriteriaQuery<T> createCriteriaQueryByProperty(
            final SingularAttribute<T, E> property, final Object value) {
        final CriteriaQuery<T> criteriaQuery = createCriteriaQuery();
        return createCriteriaQueryWithRestriction(
                getCriteriaBuilder().equal(criteriaQuery.from(getPersistenceClass()).get(property),
                        value), criteriaQuery);
    }

    /**
     * @return Criteria query with property equality.
     */
    @Override
    public TypedQuery<T> createDecoratedQueryStartEnd(final CriteriaQuery<T> query,
            final int start, final int end) {
        return createTypedQuery(query).setFirstResult(start).setMaxResults(end);
    }

    /**
     * @param property
     * @param asc
     * @return Criteria query with property equality.
     */
    @Override
    public CriteriaQuery<T> createQueryOrderedBy(final SingularAttribute<T, ?> property,
            final boolean asc) {
        final CriteriaQuery<T> query = createCriteriaQuery();
        if (StringUtils.isEmpty(property)) {
            query.from(getPersistenceClass());
            return query;
        }
        return decorateQueryOrderBy(query, property, asc);
    }

    /**
     * @param query
     * @param property
     * @param asc
     * @return Criteria query with property equality.
     */
    @Override
    public CriteriaQuery<T> decorateQueryOrderBy(final CriteriaQuery<T> query,
            final SingularAttribute<T, ?> property, final boolean asc) {
        Order order = null;
        final Path<?> path = query.from(getPersistenceClass()).get(property);
        if (asc) {
            order = getCriteriaBuilder().asc(path);
        } else {
            order = getCriteriaBuilder().desc(path);
        }
        return query.orderBy(order);
    }

    /**
     * @param property
     *            any property.
     * @param value
     *            of the property.
     * @return Criteria query with property equality.
     */
    @Override
    public CriteriaQuery<T> createCriteriaQueryLikeProperty(final String property,
            final String value) {
        final CriteriaQuery<T> criteriaQuery = createCriteriaQuery();
        return createCriteriaQueryWithRestriction(
                getCriteriaBuilder().like(
                        criteriaQuery.from(getPersistenceClass()).<String> get(property), value),
                criteriaQuery);
    }

    /**
     * @param restriction
     *            restriction.
     * @return Criteria query with property equality.
     */
    @Override
    public CriteriaQuery<T> createCriteriaQueryWithRestriction(
            final Expression<Boolean> restriction, final CriteriaQuery<T> criteriaQuery) {
        criteriaQuery.select(criteriaQuery.from(getPersistenceClass()));
        criteriaQuery.where(restriction);
        return criteriaQuery;
    }

    /**
     * @return the manager
     */
    public final EntityManager getManager() {
        return entityManager;
    }

    /**
     * @param manager
     *            the manager to set
     */
    final void setManager(final EntityManager manager) {
        Assert.notNull(manager);
        this.entityManager = manager;
    }

    /**
     * @return the persistanceClass
     */
    @Override
    public final Class<T> getPersistenceClass() {
        return persistanceClass;
    }

    /**
     * @param persistanceClass
     *            the persistanceClass to set
     */
    final void setPersistanceClass(final Class<T> persistanceClass) {
        Assert.notNull(persistanceClass);
        this.persistanceClass = persistanceClass;
    }

    @Override
    public Root<T> createRoot() {
        return createRoot(createCriteriaQuery());
    }

    @Override
    public Expression<Long> count(final CriteriaQuery<T> criteriaQuery) {
        return getCriteriaBuilder().count(createRoot(criteriaQuery));
    }

    @Override
    public Root<T> createRoot(final CriteriaQuery<T> criteriaQuery) {
        Assert.notNull(criteriaQuery);
        return criteriaQuery.from(getPersistenceClass());
    }

    @Override
    public CriteriaQuery<T> createCriteriaQueryLimited(final int startIndex, final int endIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <E> CriteriaQuery<T> createCriteriaQuerySorted(final SingularAttribute<T, E> sortproperty) {
        throw new UnsupportedOperationException();
    }
}
