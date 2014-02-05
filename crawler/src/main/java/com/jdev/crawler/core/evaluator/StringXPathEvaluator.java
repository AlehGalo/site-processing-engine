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
public class StringXPathEvaluator extends AbstractXPathEvaluator<String> {

    /**
     * @param expression
     * @param node
     */
    public StringXPathEvaluator(final String expression, final Node node) {
        super(expression, node, XPathConstants.STRING);
    }

    @Override
    public String evaluate() {
        return commonEvaluate();
    }

}
