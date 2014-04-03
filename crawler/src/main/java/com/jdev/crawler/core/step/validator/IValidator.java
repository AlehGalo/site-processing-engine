package com.jdev.crawler.core.step.validator;

import com.jdev.crawler.core.process.model.IEntity;

/**
 * @author Aleh Validates the Entity. Simplest validation.
 */
public interface IValidator {

    /**
     * @param entity
     *            data.
     * @return true/false.
     */
    boolean validate(IEntity entity);
}
