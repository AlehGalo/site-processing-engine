package com.jdev.crawler.util.xpath;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;

import com.jdev.crawler.util.Assert;

/**
 * XPath evaluate expression tool.
 */
public final class XPathEvaluator {

    private static final XPathFactory FACTORY = XPathFactory.newInstance();

    /**
     * Hide public constructor.
     */
    private XPathEvaluator() {
    }

    /**
     * <p>
     * Evaluate the compiled XPath expression in the specified context and
     * return the result as the specified type.
     * </p>
     * 
     * @param expression
     *            The XPath expression.
     * @param node
     *            The starting context.
     * @param returnType
     *            The desired return type.
     * @return The <code>Object</code> that is the result of evaluating the
     *         expression and converting the result to <code>returnType</code>.
     * @throws XPathExpressionException
     */
    public static Object eval(final String expression, final Node node, final QName returnType)
            throws XPathExpressionException {
        Assert.notNull(node);
        Assert.hasLength(expression);
        Assert.notNull(returnType);
        final XPath xPath = FACTORY.newXPath();
        final XPathExpression xPathExpression = xPath.compile(expression);
        return xPathExpression.evaluate(node, returnType);
    }
}