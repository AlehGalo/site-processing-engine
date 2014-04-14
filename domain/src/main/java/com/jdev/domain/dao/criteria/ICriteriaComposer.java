/**
 * 
 */
package com.jdev.domain.dao.criteria;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 * @author Aleh
 * @param <T>
 */
public interface ICriteriaComposer<T> {

    /**
     * @return Criteria builder.
     */
    CriteriaBuilder getCriteriaBuilder();

    /**
     * @param clazz
     * @return Criteria query.
     */
    <E> CriteriaQuery<E> createCriteriaQuery(final Class<E> clazz);

    /**
     * @return Criteria query.
     */
    CriteriaQuery<T> createCriteriaQuery();

    /**
     * @return Criteria query.
     */
    TypedQuery<T> createTypedQuery();

    /**
     * @param query
     * @return Criteria query.
     */

    <E> TypedQuery<E> createTypedQuery(final CriteriaQuery<E> query);

    /**
     * @param property
     *            any property.
     * @param value
     *            of the property.
     * @return Criteria query with property equality.
     */
    CriteriaQuery<T> createCriteriaQueryByStringProperty(final String property, final Object value);

    /**
     * @param property
     *            any property.
     * @param value
     *            of the property.
     * @return Criteria query with property equality.
     */
    <E> CriteriaQuery<T> createCriteriaQueryByProperty(final SingularAttribute<T, E> property,
            final Object value);

    /**
     * @param query
     * @param start
     * @param end
     * @return Criteria query with property equality.
     */
    TypedQuery<T> createDecoratedQueryStartEnd(final CriteriaQuery<T> query, final int start,
            final int end);

    /**
     * @param property
     * @param asc
     * @return Criteria query with property equality.
     */
    CriteriaQuery<T> createQueryOrderedBy(final String property, final boolean asc);

    /**
     * @param query
     * @param property
     * @param asc
     * @return Criteria query with property equality.
     */
    CriteriaQuery<T> decorateQueryOrderBy(final CriteriaQuery<T> query, final String property,
            final boolean asc);

    /**
     * @param property
     *            any property.
     * @param value
     *            of the property.
     * @return Criteria query with property equality.
     */
    CriteriaQuery<T> createCriteriaQueryLikeProperty(final String property, final String value);

    /**
     * @param restriction
     *            restriction.
     * @param criteriaQuery
     * @return Criteria query with property equality.
     */
    CriteriaQuery<T> createCriteriaQueryWithRestriction(final Expression<Boolean> restriction,
            final CriteriaQuery<T> criteriaQuery);

    /**
     * @return Root.
     */
    Root<T> createRoot();

    /**
     * @return Class.
     */
    Class<T> getPersistanceClass();
}