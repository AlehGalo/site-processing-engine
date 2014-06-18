/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.entity.IIdentifiable;

/**
 * @author Aleh
 * @param <T>
 */
public interface IWriteDao<T extends IIdentifiable> {

    /**
     * @param t
     *            object.
     */
    void save(T t);

    /**
     * @param t
     *            object.
     */
    void update(T t);

    /**
     * @param t
     *            object.
     */
    void delete(T t);
}
