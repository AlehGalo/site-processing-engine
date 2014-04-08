/**
 * 
 */
package com.jdev.domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh
 * 
 * @param <T>
 */
abstract class AbstractCommonGenericReadDao<T extends IIdentifiable> extends AbstractGenericDao<T>
        implements IReadDao<T> {

    @Override
    public T find(final Long id) {
        if (id == null) {
            return null;
        }
        return getEntityManager().find(getPersistentClass(), id);
    }

    @Override
    public List<T> findAll(final int startPosition, final int resultNumber) {
        return findAll(startPosition, resultNumber, null);
    }

    @Override
    public List<T> findAll(final int startPosition, final int resultNumber, final String sortBy) {
        return getCriteriaComposer().createDecoratedQueryStartEnd(
                getCriteriaComposer().createQueryOrderedBy(sortBy, false), startPosition,
                resultNumber).getResultList();
    }

    @Override
    public List<T> findAll(final int startPosition, final int resultNumber, final String sortBy,
            final Date start, final Date end) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.domain.dao.IReadDao#count(javax.persistence.criteria.CriteriaQuery
     * )
     */
    @Override
    public long count(final Date start, final Date end) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long countAll() {
        final CriteriaQuery<Long> query = getCriteriaComposer().createCriteriaQuery(Long.class);
        return getCriteriaComposer().createTypedQuery(
                query.select(getCriteriaComposer().getCriteriaBuilder().count(
                        query.from(getPersistentClass())))).getSingleResult();
    }

    @Override
    public List<T> findByStringProperty(final String property, final Object value) {
        if (StringUtils.isEmpty(property) || value == null) {
            return new ArrayList<>();
        }
        return getCriteriaComposer().createTypedQuery(
                getCriteriaComposer().createCriteriaQueryByStringProperty(property, value))
                .getResultList();
    }

    @Override
    public List<T> findAll(final Expression<Boolean> restriction, final T t) {
        return getCriteriaComposer().createTypedQuery(
                getCriteriaComposer().createCriteriaQueryWithRestriction(restriction,
                        getCriteriaComposer().createCriteriaQuery())).getResultList();
    }

    @Override
    public List<T> find(final CriteriaQuery<T> query) {
        Assert.notNull(query);
        return getCriteriaComposer().createTypedQuery(query).getResultList();
    }
}
