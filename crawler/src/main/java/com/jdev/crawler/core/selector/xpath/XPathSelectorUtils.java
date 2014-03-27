/**
 * 
 */
package com.jdev.crawler.core.selector.xpath;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jdev.crawler.core.evaluator.NodeListXPathEvaluator;
import com.jdev.crawler.core.evaluator.NodeXPathEvaluator;
import com.jdev.crawler.core.evaluator.StringXPathEvaluator;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.XPathSelectionException;

/**
 * @author Aleh
 * 
 */
public final class XPathSelectorUtils {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(XPathSelectorUtils.class);

    /**
     * @param name
     * @param xPath
     * @param node
     * @return
     * @throws XPathSelectionException
     * @throws XPathExpressionException
     */
    public static final List<ISelectorResult> selectFromNodeList(final String name,
            final String xPath, final Node node) throws XPathExpressionException {
        final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
        final NodeList nodes = new NodeListXPathEvaluator(xPath, node).evaluate();
        if (nodes != null) {
            final int length = nodes.getLength();
            if (length == 0) {
                LOGGER.debug("NodeList is empty for [name={}] [selector={}]", name, xPath);
                return resultList;
            }
            for (int i = 0; i < length; i++) {
                final Node nodeLoc = nodes.item(i);
                final String nodeValue = nodeLoc.getNodeValue();
                if (StringUtils.isNotBlank(nodeValue)) {
                    resultList.add(new SelectorResult(name, nodeValue));
                }
                LOGGER.debug("Selected {} {}", name, nodeValue);
            }
        } else {
            LOGGER.debug("Evaluation is not a node list for [name={}] [selector={}]");
        }
        return resultList;
    }

    /**
     * @param name
     * @param xPath
     * @param node
     * @return
     * @throws XPathExpressionException
     */
    public static final List<ISelectorResult> selectFromNode(final String name, final String xPath,
            final Node node) throws XPathExpressionException {
        final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
        final Node nodeSelected = new NodeXPathEvaluator(xPath, node).evaluate();
        if (nodeSelected != null) {
            final String value = nodeSelected.getNodeValue();
            if (StringUtils.isNotBlank(value)) {
                resultList.add(new SelectorResult(name, value));
            }
            LOGGER.debug("Selected {} {}", name, value);
        } else {
            LOGGER.debug("Evaluation is not a node for [name={}] [selector={}]", name, xPath);
        }
        return resultList;
    }

    /**
     * @param name
     * @param xPath
     * @param node
     * @return
     * @throws XPathExpressionException
     */
    public static final List<ISelectorResult> selectFromString(final String name,
            final String xPath, final Node node) throws XPathExpressionException,
            XPathSelectionException {
        final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
        final String stringSelected = new StringXPathEvaluator(xPath, node).evaluate();
        if (StringUtils.isNotBlank(stringSelected)) {
            resultList.add(new SelectorResult(name, stringSelected));
        }
        LOGGER.debug("Selected {} {}", name, stringSelected);
        return resultList;
    }
}
