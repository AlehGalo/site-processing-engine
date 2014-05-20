package com.jdev.crawler.core.process;

import java.util.List;

import org.apache.http.client.methods.HttpRequestBase;

import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;

/**
 *
 */
public class SimpleStepProcess extends AbstractStepProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private final IStepConfig config;

    /**
     * @param config
     */
    public SimpleStepProcess(final IStepConfig config) {
        this(config, null);
    }

    /**
     * @param config
     * @param handlers
     */
    public SimpleStepProcess(final IStepConfig config, final IProcessResultHandler handler) {
        super(handler, config, "SimpleStepProcess");
        this.config = config;
    }

    @Override
    protected HttpRequestBase createRequest(final IProcessContext context,
            final List<ISelectorResult> list) throws CrawlerException {
        final IRequestBuilder requestBuilder = getRequestBuilder() == null ? context
                .getRequestBuilder() : getRequestBuilder();
        return requestBuilder.buildRequest(config.getMethod(), config.getUrl(), list);
    }
}
