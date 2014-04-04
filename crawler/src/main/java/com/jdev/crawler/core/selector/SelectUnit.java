/**
 * 
 */
package com.jdev.crawler.core.selector;

import java.io.Serializable;

import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class SelectUnit implements ISelectUnit, Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Name and selector.
     */
    private final String name, selector;

    /**
     * @param name
     * @param selector
     */
    public SelectUnit(final String name, final String selector) {
        Assert.hasLength(name);
        Assert.hasLength(selector);
        this.name = name;
        this.selector = selector;
    }

    /**
     * @param word
     * @param selector
     */
    public SelectUnit(final RequestReservedWord word, final String selector) {
        Assert.notNull(word);
        Assert.hasLength(selector);
        this.name = word.getWord();
        this.selector = selector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.selector.ISelectUnit#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.selector.ISelectUnit#getSelector()
     */
    @Override
    public String getSelector() {
        return this.selector;
    }

}
