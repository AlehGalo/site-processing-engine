package com.jdev.crawler.core.selector.content;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.jdev.crawler.util.Assert;

/**
 * XPath evaluate expression tool.
 */
public final class XPathEvaluator implements IEvaluator<Object> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XPathEvaluator.class);

    /**
     * Xpath factory.
     */
    private static final XPathFactory FACTORY = XPathFactory.newInstance();

    /**
     * XPath expression.
     */
    private final String expression;

    /**
     * Node which content is the basic source.
     */
    private final Node node;

    /**
     * Return type.
     */
    private final QName returnType;

    /**
     * @param expression
     * @param node
     * @param returnType
     */
    public XPathEvaluator(final String expression, final Node node, final QName returnType) {
        Assert.notNull(node);
        Assert.hasLength(expression);
        Assert.notNull(returnType);
        this.expression = expression;
        this.node = node;
        this.returnType = returnType;
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
    @Override
    public Object evaluate() {
        try {
            return FACTORY.newXPath().compile(expression).evaluate(node, returnType);
        } catch (XPathExpressionException ex) {
            LOGGER.error(ex.getMessage());
            return new EmptyObject();
        }
    }
}