/**
 * 
 */
package com.jdev.crawler.core.process;

import java.util.HashMap;
import java.util.Map;

import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class ProcessSession implements IProcessSession {

    /**
     * Process context.
     */
    private final IProcessContext context;

    /**
     * 
     */
    private final Map<String, Object> map;

    /**
     * 
     */
    public ProcessSession(final IProcessContext context) {
        Assert.notNull(context);
        this.context = context;
        map = new HashMap<String, Object>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IProcessSession#getSessionContext()
     */
    @Override
    public IProcessContext getSessionContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IProcessSession#putValue(java.lang
     * .String, java.lang.Object)
     */
    @Override
    public Object putValue(final String word, final Object value) {
        Assert.hasLength(word);
        return map.put(word, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IProcessSession#getValue(java.lang
     * .String)
     */
    @Override
    public Object getValue(final String key) {
        Assert.notNull(key);
        return map.get(key);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IProcessSession#getStringValue(java
     * .lang .String)
     */
    @Override
    public String getStringValue(final String key) {
        Assert.notNull(key);
        final Object value = map.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

}
