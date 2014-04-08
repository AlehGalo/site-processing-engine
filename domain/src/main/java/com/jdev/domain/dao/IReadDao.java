/**
 * 
 */
package com.jdev.domain.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import com.jdev.domain.dao.criteria.ICriteriaComposer;
import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh Common read dao.
 * @param <T>
 * @param <Y>
 */
public interface IReadDao<T extends IIdentifiable> {

    /**
     * @param id
     * @return
     */
    T find(final Long id);

    /**
     * @param restriction
     * @param t
     * @return set of objects.
     */
    List<T> findAll(final Expression<Boolean> restriction, final T t);

    /**
     * @param startPosition
     * @param resultNumber
     * @return set of objects.
     */
    List<T> findAll(final int startPosition, final int resultNumber);

    /**
     * @param startPosition
     * @param resultNumber
     * @param sortBy
     * @return set of objects.
     */
    List<T> findAll(final int startPosition, final int resultNumber, final String sortBy);

    /**
     * @param startPosition
     * @param resultNumber
     * @param sortBy
     * @param start
     * @param end
     * @return set of objects.
     */
    List<T> findAll(final int startPosition, final int resultNumber, final String sortBy,
            Date start, Date end);

    /**
     * @param criteriaComposer
     *            composer.
     * @return List of objects.
     */
    List<T> find(CriteriaQuery<T> criteriaComposer);

    /**
     * @return number of records in db.
     */
    long countAll();

    /**
     * @param start
     * @param end
     * @return
     */
    long count(final Date start, final Date end);

    /**
     * @param property
     * @param value
     * @return
     */
    List<T> findByStringProperty(final String property, final Object value);

    /**
     * @return Criteria composer.
     */
    ICriteriaComposer<T> getCriteriaComposer();
}
