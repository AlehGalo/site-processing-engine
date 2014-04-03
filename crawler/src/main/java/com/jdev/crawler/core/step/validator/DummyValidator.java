package com.jdev.crawler.core.step.validator;

import com.jdev.crawler.core.process.model.IEntity;

/**
 * @author Aleh Simple dummy validator that always return true.
 */
public class DummyValidator implements IValidator {

    @Override
    public boolean validate(final IEntity content) {
        return true;
    }
}
