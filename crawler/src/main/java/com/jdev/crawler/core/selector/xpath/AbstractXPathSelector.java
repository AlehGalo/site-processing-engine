/**
 * 
 */
package com.jdev.crawler.core.selector.xpath;

import static com.jdev.crawler.core.selector.xpath.XPathSelectorUtils.selectFromNode;
import static com.jdev.crawler.core.selector.xpath.XPathSelectorUtils.selectFromNodeList;
import static com.jdev.crawler.core.selector.xpath.XPathSelectorUtils.selectFromString;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.XPathSelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
abstract class AbstractXPathSelector<T> implements ISelector<T> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractXPathSelector.class);

    /**
     * Node element.
     */
    private Node node;

    /**
     * Selection unit.
     */
    private final ISelectUnit selectUnit;

    /**
     * @param selectUnit
     *            unit.
     */
    public AbstractXPathSelector(final ISelectUnit selectUnit) {
        Assert.notNull(selectUnit);
        this.selectUnit = selectUnit;
    }

    /**
     * XPath processing.
     * 
     * @return
     * @throws XPathSelectionException
     */
    protected final List<ISelectorResult> evaluateXPath(final ISelectUnit selectionUnit)
            throws XPathSelectionException {
        Assert.notNull(selectionUnit);
        final List<ISelectorResult> resultList = new ArrayList<>();
        if (node != null) {
            try {
                resultList.addAll(selectFromNodeList(selectionUnit, node));
                if (resultList.isEmpty()) {
                    resultList.addAll(selectFromNode(selectionUnit, node));
                }
                if (resultList.isEmpty()) {
                    resultList.addAll(selectFromString(selectionUnit, node));
                }
            } catch (final XPathExpressionException e) {
                throw new XPathSelectionException(selectionUnit.getName(),
                        selectionUnit.getSelector(), e);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Results found {}", resultList.size());
        }
        return resultList;
    }

    /**
     * @param node
     *            the node to set
     */
    public final void setNode(final Node node) {
        this.node = node;
    }

    /**
     * @return the node
     */
    public final Node getNode() {
        return node;
    }

    /**
     * @return the selectUnit
     */
    protected final ISelectUnit getSelectUnit() {
        return selectUnit;
    }
}
