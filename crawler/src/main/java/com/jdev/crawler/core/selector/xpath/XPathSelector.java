/**
 *
 */
package com.jdev.crawler.core.selector.xpath;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jdev.crawler.config.dom.TagSoupDomNormaliser;
import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.exception.XPathSelectionException;

/**
 * @author Aleh
 */
public class XPathSelector extends AbstractXPathSelector<String> {

    /**
     * @param name
     * @param selector
     */
    public XPathSelector(final ISelectUnit unit) {
        super(unit);
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
        return evaluateXPath(getSelectUnit());
    }
}