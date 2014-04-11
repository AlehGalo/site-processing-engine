/**
 * 
 */
package com.jdev.crawler.core.selector.entity;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public abstract class EntitySelector implements ISelector<HttpEntity> {

    /**
     * Select unit.
     */
    private final ISelectUnit unit;

    /**
     * @param unit
     */
    public EntitySelector(final ISelectUnit unit) {
        Assert.notNull(unit);
        this.unit = unit;
    }

    /**
     * @return the unit
     */
    protected final ISelectUnit getUnit() {
        return unit;
    }

    final Set<ISelectorResult> getSelectionResultFromHeaderElements(final HeaderElement[] elements) {
        Set<ISelectorResult> set = new HashSet<>();
        if (ArrayUtils.isNotEmpty(elements)) {
            String name = getUnit().getName();
            for (HeaderElement headerElement : elements) {
                NameValuePair pair = headerElement.getParameterByName(name);
                if (pair != null) {
                    set.add(new SelectorResult(pair.getName(), pair.getValue()));
                }
            }
        }
        return set;
    }
}