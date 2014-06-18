/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.entity.IIdentifiable;

/**
 * @author Aleh
 * @param <T>
 */
public class CommonGenericWriteDao<T extends IIdentifiable> extends AbstractGenericDao<T> implements
        IWriteDao<T> {

    /**
     * @param clazz
     *            Generic class.
     */
    public CommonGenericWriteDao(final Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void save(final T t) {
        getEntityManager().persist(t);
    }

    @Override
    public void delete(final T t) {
        getEntityManager().remove(t);
    }

    @Override
    public void update(final T t) {
        getEntityManager().merge(t);
    }
}
