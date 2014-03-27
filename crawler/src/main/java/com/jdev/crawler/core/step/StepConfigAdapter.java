/**
 *
 */
package com.jdev.crawler.core.step;

import java.util.Collection;
import java.util.Collections;

import com.jdev.crawler.core.selector.ISelector;

/**
 * @author Aleh
 */
public class StepConfigAdapter implements IStepConfig {

    /*
     * (non-Javadoc)
     * 
     * @see cinergy.crawler.core.step.IStepConfig#getUrl()
     */
    @Override
    public String getUrl() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cinergy.crawler.core.step.IStepConfig#getParameters()
     */
    @Override
    public Collection<ISelector<?>> getParameters() {
        return Collections.<ISelector<?>> emptyList();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cinergy.crawler.core.step.IStepConfig#getMethod()
     */
    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.GET;
    }

}
