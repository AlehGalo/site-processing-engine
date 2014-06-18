/**
 * 
 */
package com.jdev.collector.job.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Aleh
 * 
 */
public class CommonEntityValidator<T> implements IValidator {

    /**
     * Object.
     */
    private T t;

    /**
     * Javax validator.
     */
    private final Validator validator;

    /**
     * 
     */
    private Set<ConstraintViolation<T>> setOfConstraints;

    /**
     * Constructor.
     */
    public CommonEntityValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.step.validator.IValidator#validate(com.jdev.crawler
     * .core.process.model.IEntity)
     */
    @Override
    public boolean validate() {
        setOfConstraints = validator.validate(getEntity());
        return CollectionUtils.isEmpty(setOfConstraints);
    }

    /**
     * @return the t
     */
    public final T getEntity() {
        return t;
    }

    /**
     * @param t
     *            the t to set
     */
    public final void setEntity(final T t) {
        this.t = t;
    }

    /**
     * @return the setOfConstraints
     */
    public final Set<ConstraintViolation<T>> getSetOfConstraints() {
        return setOfConstraints;
    }

}
