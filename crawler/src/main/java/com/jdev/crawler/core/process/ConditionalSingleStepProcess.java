package com.jdev.crawler.core.process;

import java.util.Collections;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.core.step.IValidator;

public class ConditionalSingleStepProcess extends SimpleStepProcess implements IConditionalProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    private final IValidator validator;

    public ConditionalSingleStepProcess(IValidator validator, IStepConfig config) {
        super(config, Collections.<IProcessResultHandler> emptyList());
        this.validator = validator;
    }

    @Override
    public boolean match(IEntity content) {
        return validator.validate(content);
    }
}
