/**
 * 
 */
package com.jdev.crawler.core.evaluator;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.xpath.XPathEvaluator;

/**
 * @author Aleh
 * 
 */
public final class XPathEvaluatorUtils {

    /**
     * 
     */
    private XPathEvaluatorUtils() {
    }

    /**
     * Return string evaluated from node.
     * 
     * @param node
     * @param xPath
     * @return
     * @throws XPathExpressionException
     */
    public static final String getStringFromNode(final Node node, final String xPath)
            throws XPathExpressionException {
        return (String) XPathEvaluator.eval(xPath, node, XPathConstants.STRING);
    }

    /**
     * Return node evaluated from node by xpath.
     * 
     * @param node
     * @param xPath
     * @return
     * @throws XPathExpressionException
     */
    public static final Node getNodeFromNode(final Node node, final String xPath)
            throws XPathExpressionException {
        return (Node) XPathEvaluator.eval(xPath, node, XPathConstants.NODE);
    }

    /**
     * Return node list evaluated from node.
     * 
     * @param node
     * @param xPath
     * @return
     * @throws XPathExpressionException
     */
    public static final NodeList getNodesetFromNode(final Node node, final String xPath)
            throws XPathExpressionException {
        return (NodeList) XPathEvaluator.eval(xPath, node, XPathConstants.NODESET);
    }
}
