/**
 * 
 */
package com.jdev.crawler.core.evaluator;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Aleh
 * 
 */
public class NodeListXPathEvaluator extends AbstractXPathEvaluator<NodeList> {

    /**
     * @param expression
     * @param node
     */
    public NodeListXPathEvaluator(final String expression, final Node node) {
        super(expression, node, XPathConstants.NODESET);
    }

    @Override
    public NodeList evaluate() {
        return commonEvaluate();
    }
}
