/**
 * 
 */
package com.jdev.domain.domain;

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
