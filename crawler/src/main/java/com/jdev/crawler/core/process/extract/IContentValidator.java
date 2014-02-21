/**
 * 
 */
package com.jdev.crawler.core.process.extract;

/**
 * @author Aleh Validator that validate object and return true or false
 *         depending on the type of validation.
 */
public interface IContentValidator {

    /**
     * @param obj
     *            any object.
     * @return true/false
     */
    boolean isContentValid(Object obj);
}
