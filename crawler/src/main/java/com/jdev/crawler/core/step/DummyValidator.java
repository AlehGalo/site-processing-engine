package com.jdev.crawler.core.step;

public class DummyValidator implements IValidator {
    @Override
    public boolean validate(final byte[] content) {
	return true;
    }
}
