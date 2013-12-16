/**
 * 
 */
package com.jdev.crawler.core.selector.xpath;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.exception.XPathSelectionException;
import com.jdev.crawler.util.xpath.XPathEvaluator;

/**
 * @author Aleh
 * 
 */
abstract class AbstractXPathSelector implements ISelector {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AbstractXPathSelector.class);

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
    protected final List<ISelectorResult> evaluateXPath(final String name,
	    final String xPath) throws XPathSelectionException {
	final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
	if (node != null) {
	    try {
		resultList.addAll(selectFromNodeList(name, xPath));
		if (resultList.isEmpty()) {
		    resultList.addAll(selectFromNode(name, xPath));
		}
		if (resultList.isEmpty()) {
		    resultList.addAll(selectFromString(name, xPath));
		}
	    } catch (final XPathExpressionException e) {
		LOGGER.error(e.getLocalizedMessage(), e);
		throw new XPathSelectionException(name, xPath, e);
	    }
	}
	return resultList;
    }

    protected List<ISelectorResult> selectFromNodeList(final String name,
	    final String xPath) throws XPathSelectionException,
	    XPathExpressionException {
	final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
	final Object obj = XPathEvaluator.eval(xPath, node,
		XPathConstants.NODESET);
	if (obj instanceof NodeList) {
	    final NodeList nodeList = (NodeList) obj;
	    final int length = nodeList.getLength();
	    if (length == 0) {
		LOGGER.debug("NodeList is empty for [name={}] [selector={}]",
			name, xPath);
		return resultList;
	    }
	    for (int i = 0; i < length; i++) {
		final Node node = nodeList.item(i);
		final String nodeValue = node.getNodeValue();
		if (StringUtils.isEmpty(nodeValue)) {
		    LOGGER.error("Node value selected by XPath is empty {} {}",
			    name, xPath);
		    throw new XPathSelectionException(name, xPath);
		}
		resultList.add(new SelectorResult(name, nodeValue));
		LOGGER.debug("[XpathSelector] >> {} {}", name, nodeValue);
	    }
	} else {
	    LOGGER.debug("Evaluation is not a node list for [name={}] [selector={}]");
	}
	return resultList;
    }

    protected List<ISelectorResult> selectFromNode(final String name,
	    final String xPath) throws XPathExpressionException {
	final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
	final Object object = XPathEvaluator.eval(xPath, node,
		XPathConstants.NODE);
	if (object instanceof Node) {
	    final String value = ((Node) object).getNodeValue();
	    resultList.add(new SelectorResult(name, value));
	    LOGGER.debug("[XpathSelector]> {} {}", name, value);
	} else {
	    LOGGER.debug(
		    "Evaluation is not a node for [name={}] [selector={}]",
		    name, xPath);
	}
	return resultList;
    }

    protected List<ISelectorResult> selectFromString(final String name,
	    final String xPath) throws XPathExpressionException {
	final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
	final Object object = XPathEvaluator.eval(xPath, node,
		XPathConstants.STRING);
	if (object instanceof String && StringUtils.isNotEmpty((String) object)) {
	    final String value = ((Node) object).getNodeValue();
	    resultList.add(new SelectorResult(name, value));
	    LOGGER.debug("[XpathSelector]> {} {}", name, value);
	} else {
	    LOGGER.debug(
		    "Evaluation is not a string for [name={}] [selector={}]",
		    name, xPath);
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
