/**
 * 
 */
package com.jdev.crawler.core.process.container;

import java.util.List;

import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public abstract class AbstractBaseProcessContainer<T> implements IProcess {

    /**
     * 
     */
    private final List<T> elements;

    /**
     * @param elements
     */
    public AbstractBaseProcessContainer(final List<T> elements) {
        Assert.notNull(elements);
        this.elements = elements;
    }

    /**
     * @return the elements
     */
    protected final List<T> getElements() {
        return elements;
    }

}
