/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh
 * @param <T>
 */
class CommonGenericWriteDao<T extends IIdentifiable> extends
        CommonGenericReadDao<T> implements IWriteDao<T> {

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
