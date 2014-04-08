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
import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.exception.XPathSelectionException;

/**
 * @author Aleh
 * 
 */
public class XpathMultiSelector extends AbstractXPathSelector<String> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XpathMultiSelector.class);

    /**
     * @param unit
     */
    public XpathMultiSelector(final ISelectUnit unit) {
        super(unit);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.selector.ISelector#select(java.lang.Object)
     */
    @Override
    public Collection<ISelectorResult> select(final String content) throws SelectionException {
        setNode(TagSoupDomNormaliser.convertToNormalisedNode(content));
        final List<ISelectorResult> names = selectNames();
        final List<ISelectorResult> values = selectValues();
        final List<ISelectorResult> result = new ArrayList<ISelectorResult>();
        if (names.size() == 0 || names.size() != values.size()) {
            LOGGER.error("[XpathMultiSelector] Names size {} and values size {} are incorrect.",
                    names.size(), values.size());
            throw new XPathSelectionException(getSelectUnit().getName(), getSelectUnit()
                    .getSelector());
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
        return evaluateXPath(new SelectUnit("name", getSelectUnit().getName()));
    }

    /**
     * @return
     * @throws XPathSelectionException
     */
    private List<ISelectorResult> selectValues() throws XPathSelectionException {
        return evaluateXPath(new SelectUnit("value", getSelectUnit().getSelector()));
    }

}
