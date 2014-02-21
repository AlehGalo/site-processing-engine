/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XPathSelector.class);

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
        setNode(TagSoupDomNormaliser.convertToNormalisedNode(content));
        final List<ISelectorResult> list = evaluateXPath(name, selector);
        if (list.isEmpty()) {
            LOGGER.error("XPath selector extracted 0 items for {} {}", name, selector);
            throw new XPathSelectionException(name, selector);
        }
        return list;
    }
}