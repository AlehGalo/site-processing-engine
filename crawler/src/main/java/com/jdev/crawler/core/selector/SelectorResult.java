/**
 *
 */
package com.jdev.crawler.core.selector;

/**
 * @author Aleh
 */
public class SelectorResult implements ISelectorResult {

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String value;

    /**
     * @param name
     * @param value
     */
    public SelectorResult(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cinergy.dre.bm.crawler.base.crawler.conf.selector.ISelectorResult#getName
     * ()
     */
    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cinergy.dre.bm.crawler.base.crawler.conf.selector.ISelectorResult#getValue
     * ()
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @param name
     *            the name to set
     */
    protected final void setName(final String name) {
        this.name = name;
    }

    /**
     * @param value
     *            the value to set
     */
    protected final void setValue(final String value) {
        this.value = value;
    }

}
