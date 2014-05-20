package com.jdev.crawler.core.process;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.core.step.validator.IValidator;

public class ConditionalSingleStepProcess extends SimpleStepProcess implements IConditionalProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private final IValidator validator;

    /**
     * @param validator
     * @param config
     */
    public ConditionalSingleStepProcess(final IValidator validator, final IStepConfig config) {
        super(config, null);
        this.validator = validator;
    }

    @Override
    public boolean match(final IEntity content) {
        return validator.validate(content);
    }
}
