package com.jdev.crawler.core.evaluator;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.jdev.crawler.util.Assert;

/**
 * XPath evaluate expression tool.
 * 
 * @param <T>
 */
public abstract class AbstractXPathEvaluator<T> implements IEvaluator<T> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractXPathEvaluator.class);

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
    AbstractXPathEvaluator(final String expression, final Node node, final QName returnType) {
        Assert.notNull(node);
        Assert.hasLength(expression);
        Assert.notNull(returnType);
        this.expression = expression;
        this.node = node;
        this.returnType = returnType;
    }

    /**
     * @return object.
     */
    @SuppressWarnings("unchecked")
    protected final T commonEvaluate() {
        try {
            return (T) FACTORY.newXPath().compile(expression).evaluate(node, returnType);
        } catch (XPathExpressionException ex) {
            LOGGER.info(ex.getCause().getMessage());
            return null;
        }
    }
}