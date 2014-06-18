/**
 * 
 */
package com.jdev.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Aleh
 * 
 */
@MappedSuperclass
abstract class AbstractIdentifiable implements IIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.domain.model.IIdentifiable#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.domain.model.IIdentifiable#setId(java.lang.Integer)
     */
    @Override
    public void setId(final Long id) {
        this.id = id;
    }
}