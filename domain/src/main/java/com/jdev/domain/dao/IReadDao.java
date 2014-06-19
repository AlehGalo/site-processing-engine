/**
 * 
 */
package com.jdev.domain.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.SingularAttribute;

import com.jdev.domain.entity.IIdentifiable;

/**
 * @author Aleh Common read dao.
 * @param <T>
 */
public interface IReadDao<T extends IIdentifiable> extends IComposer<T> {

    /**
     * @param id
     * @return
     */
    T get(final Long id);

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

    /**
     * @param start
     * @param end
     * @return
     */
    long count(CriteriaQuery<Long> criteriaQuery);

    /**
     * @return list of all objects.
     */
    List<T> findAll();

    /**
     * @param singularAttribute
     * @return
     */
    <X extends Object> List<T> findFields(List<SingularAttribute<T, X>> singularAttribute);

    /**
     * @return number of records in db.
     */
    long countAll();

}