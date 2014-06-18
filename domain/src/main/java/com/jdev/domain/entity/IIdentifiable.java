/**
 * 
 */
package com.jdev.domain.entity;

import java.io.Serializable;

/**
 * @author Aleh
 */
public interface IIdentifiable extends Serializable {

    /**
     * @return primary key for database.
     */
    Long getId();

    /**
     * @param id
     *            unique number.
     */
    void setId(Long id);
}
