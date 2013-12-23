/**
 * 
 */
package com.jdev.crawler.core.selector.xpath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.config.dom.TagSoupDomNormaliser;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.exception.XPathSelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class XpathMultiSelector extends AbstractXPathSelector {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XpathMultiSelector.class);
    /**
     * 
     */
    private final String name;

    /**
     * 
     */
    private final String selector;

    /**
     * @param nameSelector
     * @param valueSelector
     */
    public XpathMultiSelector(final String nameSelector, final String valueSelector) {
        Assert.hasLength(nameSelector);
        Assert.hasLength(valueSelector);
        this.name = nameSelector;
        this.selector = valueSelector;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sswf.crawler.core.selector.ISelector#selectValues(java.lang.Object)
     */
    @Override
    public Collection<ISelectorResult> selectValues(final Object content) throws SelectionException {
        Assert.isInstanceOf(String.class, content);
        setNode(TagSoupDomNormaliser.convertToNormalisedNode((String) content));
        final List<ISelectorResult> names = selectNames();
        final List<ISelectorResult> values = selectValues();
        final List<ISelectorResult> result = new ArrayList<ISelectorResult>();
        if (names.size() == 0 || names.size() != values.size()) {
            LOGGER.error("Names size {} and values size {} are incorrect.", names.size(),
                    values.size());
            throw new XPathSelectionException(name, selector);
        }
        for (int i = 0; i < names.size(); i++) {
            result.add(new SelectorResult(names.get(i).getValue(), values.get(i).getValue()));
        }
        return result;
    }

    /**
     * @return
     * @throws XPathSelectionException
     */
    private List<ISelectorResult> selectNames() throws XPathSelectionException {
        return evaluateXPath("name", name);
    }

    /**
     * @return
     * @throws XPathSelectionException
     */
    private List<ISelectorResult> selectValues() throws XPathSelectionException {
        return evaluateXPath("value", selector);
    }
}
