package com.jdev.crawler.core.process;

import static com.jdev.crawler.core.selector.RequestReservedWord.ACTION;
import static com.jdev.crawler.core.selector.RequestReservedWord.HOST;
import static com.jdev.crawler.core.selector.RequestReservedWord.METHOD;
import static com.jdev.crawler.core.step.HTTPMethod.valueOf;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.HTTPMethod;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class AssembledStepProcess extends AbstractStepProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleStepProcess.class);

    /**
     * @param config
     */
    public AssembledStepProcess(final IStepConfig config) {
        this(config, null);
    }

    /**
     * @param config
     * @param handlers
     */
    public AssembledStepProcess(final IStepConfig config, final IProcessResultHandler handler) {
        super(handler, config, "AssembledStepProcess");
    }

    /**
     * @param config
     *            configuration for the run process.
     * @param handler
     *            processing result handlers. As usual PDF download, CSV
     *            download.
     * @param requestBuilder
     *            request builder can be used not only for the whole crawler
     *            context but for the step.
     */
    public AssembledStepProcess(final IStepConfig config, final IProcessResultHandler handler,
            final IRequestBuilder requestBuilder) {
        super(handler, config, "");
        setRequestBuilder(requestBuilder);
    }

    @Override
    protected HttpRequestBase createRequest(final IProcessContext context,
            List<ISelectorResult> list) throws CrawlerException {
        Assert.notEmpty(list);
        HTTPMethod method = null;
        final String params[] = new String[2];
        final Map<String, ISelectorResult> map = new HashMap<String, ISelectorResult>();
        for (final ISelectorResult iSelectorResult : list) {
            final String name = iSelectorResult.getName(), value = iSelectorResult.getValue();
            if (HOST.getWord().equalsIgnoreCase(name)) {
                params[0] = value;
            } else if (ACTION.getWord().equalsIgnoreCase(name)) {
                params[1] = value;
            } else if (METHOD.getWord().equalsIgnoreCase(name)) {
                String valueFromSelector = iSelectorResult.getValue();
                try {
                    method = valueOf(valueFromSelector);
                } catch (IllegalArgumentException | NullPointerException e) {
                    throw new SelectionException(MessageFormat.format(
                            "Invalid value for method name {0}", valueFromSelector), e);
                }
            } else {
                map.put(name, iSelectorResult);
            }
            log(name, value);
        }
        list = new ArrayList<ISelectorResult>(map.values());
        final IRequestBuilder requestBuilder = getRequestBuilder() == null ? context
                .getRequestBuilder() : getRequestBuilder();
        return requestBuilder.buildRequest(method, extractUrl(params), list);
    }

    /**
     * @param name
     * @param value
     */
    private void log(final String name, final String value) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(" > {}  {}", name, value);
        }
    }

    /**
     * @param args
     *            string[0] - host, string[1] - action.
     * @return final url based on host and action.
     * @throws SelectionException
     *             ex.
     */
    protected String extractUrl(final String[] args) throws SelectionException {
        String result = null;
        if (ArrayUtils.isNotEmpty(args)) {
            final StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (StringUtils.isNotEmpty(args[i])) {
                    builder.append(args[i]);
                }
            }
            result = builder.toString();
        }
        if (StringUtils.isEmpty(result)) {
            throw new SelectionException(String.format(
                    "Error creating URL with host[%s] and action[%s]", args[0], args[1]));
        }
        LOGGER.debug("URL >> " + result);
        return result;
    }
}
