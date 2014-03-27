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

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.XPathSelectionException;

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
     * XPath processing.
     * 
     * @return
     * @throws XPathSelectionException
     */
    protected final List<ISelectorResult> evaluateXPath(final String name, final String xPath)
            throws XPathSelectionException {
        final List<ISelectorResult> resultList = new ArrayList<>();
        if (node != null) {
            try {
                resultList.addAll(selectFromNodeList(name, xPath, node));
                if (resultList.isEmpty()) {
                    resultList.addAll(selectFromNode(name, xPath, node));
                }
                if (resultList.isEmpty()) {
                    resultList.addAll(selectFromString(name, xPath, node));
                }
            } catch (final XPathExpressionException e) {
                throw new XPathSelectionException(name, xPath, e);
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
}
