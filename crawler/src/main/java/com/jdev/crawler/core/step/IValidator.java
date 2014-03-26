package com.jdev.crawler.core.step;

import com.jdev.crawler.core.process.model.IEntity;

public interface IValidator {
    boolean validate(IEntity entity);
}
