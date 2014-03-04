/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jdev.crawler.config.dom.TagSoupDomNormaliser;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.exception.XPathSelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 */
public class XPathSelector extends AbstractXPathSelector<String> {

    /**
     * Name of parameter.
     */
    private final String name;

    /**
     * XPath selector.
     */
    private final String selector;

    /**
     * @param name
     * @param selector
     */
    public XPathSelector(final String name, final String selector) {
        Assert.hasLength(name);
        Assert.hasLength(selector);
        this.name = name;
        this.selector = selector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cinergy.dre.bm.crawler.base.crawler.conf.selector.ISelector#selectValues
     * (java.lang.String, java.lang.String)
     */
    @Override
    public List<ISelectorResult> select(final String content) throws SelectionException {
        if (StringUtils.isBlank(content)) {
            throw new XPathSelectionException("Content cannot be null or empty for selector");
        }
        setNode(TagSoupDomNormaliser.convertToNormalisedNode(content));
        return evaluateXPath(name, selector);
    }
}