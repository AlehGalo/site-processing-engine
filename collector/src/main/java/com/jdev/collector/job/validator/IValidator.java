/**
 * 
 */
package com.jdev.collector.job.validator;

/**
 * @author Aleh
 * 
 */
public interface IValidator {

    /**
     * @return true/false.
     */
    <T> boolean validate(T object);

}
