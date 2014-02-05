/**
 * 
 */
package com.jdev.crawler.core.evaluator;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Node;

/**
 * @author Aleh
 * 
 */
public class NodeXPathEvaluator extends AbstractXPathEvaluator<Node> {

    /**
     * @param expression
     * @param node
     */
    public NodeXPathEvaluator(final String expression, final Node node) {
        super(expression, node, XPathConstants.NODE);
    }

    @Override
    public Node evaluate() {
        return commonEvaluate();
    }

}
