/**
 * 
 */
package com.jdev.domain.dao;

import java.util.List;

import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh
 * 
 */
public interface IReadAllDao<T extends IIdentifiable> extends IReadDao<T> {

    /**
     * @return list of all objects.
     */
    List<T> findAll();

    /**
     * @return number of records in db.
     */
    long countAll();

}
