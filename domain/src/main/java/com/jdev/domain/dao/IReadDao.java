/**
 * 
 */
package com.jdev.domain.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh Common read dao.
 * @param <T>
 */
public interface IReadDao<T extends IIdentifiable> {

    /**
     * @param id
     * @return
     */
    T get(final Long id);

    // /**
    // * @return list of all objects.
    // */
    // List<T> findAll();

    /**
     * @param restriction
     * @return set of objects.
     */
    List<T> find(final Expression<Boolean> restriction);

    /**
     * @param criteriaComposer
     *            composer.
     * @return List of objects.
     */
    List<T> find(CriteriaQuery<T> criteriaQuery);

    // /**
    // * @return number of records in db.
    // */
    // long countAll();

    /**
     * @param start
     * @param end
     * @return
     */
    long count(CriteriaQuery<Long> criteriaQuery);
}