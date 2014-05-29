/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.dao.criteria.ICriteriaComposer;

/**
 * @author Aleh
 * 
 */
public interface IComposer<T> {

    /**
     * 
     */
    ICriteriaComposer<T> getCriteriaComposer();
}
