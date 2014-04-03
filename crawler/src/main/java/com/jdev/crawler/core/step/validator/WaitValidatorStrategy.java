/**
 * 
 */
package com.jdev.crawler.core.step.validator;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.apache.commons.collections.functors.NotNullPredicate;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
@Deprecated
public class WaitValidatorStrategy implements IValidator {

    /**
     * Set of validators.
     */
    private final Set<IValidator> setOfValidators;

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    public WaitValidatorStrategy() {
        setOfValidators = SetUtils.predicatedSet(new HashSet<>(), NotNullPredicate.getInstance());
    }

    /**
     * @param validator
     */
    public void addValidator(final IValidator validator) {
        Assert.notNull(validator);
        setOfValidators.add(validator);
    }

    @Override
    public boolean validate(final IEntity entity) {
        for (IValidator validator : setOfValidators) {
            if (validator.validate(entity)) {
            } else {
                return false;
            }
        }
        return false;
    }

}
