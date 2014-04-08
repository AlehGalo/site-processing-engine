/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh
 * @param <T>
 */
abstract class AbstractCommonGenericWriteDao<T extends IIdentifiable> extends
        AbstractCommonGenericReadDao<T> implements IWriteDao<T> {

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
