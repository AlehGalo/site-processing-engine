/**
 * 
 */
package com.jdev.collector.job.validator;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Aleh
 * 
 */
public class CommonEntityValidator implements IValidator {

    /**
     * Javax validator.
     */
    private final Validator validator;

    /**
     * 
     */
    private final Set<ConstraintViolation<?>> setOfConstraints;

    /**
     * Constructor.
     */
    public CommonEntityValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        setOfConstraints = new HashSet<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.step.validator.IValidator#validate(com.jdev.crawler
     * .core.process.model.IEntity)
     */
    @Override
    public <T> boolean validate(final T entity) {
        setOfConstraints.clear();
        setOfConstraints.addAll(validator.<T> validate(entity));
        return CollectionUtils.isEmpty(setOfConstraints);
    }

    /**
     * @return the setOfConstraints
     */
    public final Set<ConstraintViolation<?>> getfConstraints() {
        return setOfConstraints;
    }

}
