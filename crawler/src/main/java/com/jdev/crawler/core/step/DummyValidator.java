package com.jdev.crawler.core.step;

/**
 * @author Aleh Simple dummy validator that always return true.
 */
public class DummyValidator implements IValidator {

    @Override
    public boolean validate(final byte[] content) {
        return true;
    }
}
