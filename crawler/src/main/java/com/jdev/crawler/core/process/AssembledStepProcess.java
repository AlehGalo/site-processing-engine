package com.jdev.crawler.core.process;

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
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

public class AssembledStepProcess extends AbstractStepProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleStepProcess.class);

    public AssembledStepProcess(final IStepConfig config, final List<IProcessResultHandler> handlers) {
        super(handlers, config, "");
    }

    /**
     * @param config
     *            configuration for the run process.
     * @param handlers
     *            processing result handlers. As usual PDF download, CSV
     *            download.
     * @param requestBuilder
     *            request builder can be used not only for the whole crawler
     *            context but for the step.
     */
    public AssembledStepProcess(final IStepConfig config,
            final List<IProcessResultHandler> handlers, final IRequestBuilder requestBuilder) {
        super(handlers, config, "");
        setRequestBuilder(requestBuilder);
    }

    @Override
    protected HttpRequestBase createRequest(final IProcessContext context,
            List<ISelectorResult> list) throws CrawlerException {
        Assert.notEmpty(list);
        String method = null;
        final String params[] = new String[2];
        final Map<String, ISelectorResult> map = new HashMap<String, ISelectorResult>();
        for (final ISelectorResult iSelectorResult : list) {
            final String name = iSelectorResult.getName(), value = iSelectorResult.getValue();
            if (RequestReservedWord.HOST.getWord().equalsIgnoreCase(name)) {
                params[0] = value;
            } else if (RequestReservedWord.ACTION.getWord().equalsIgnoreCase(name)) {
                params[1] = value;
            } else if (RequestReservedWord.METHOD.getWord().equalsIgnoreCase(name)) {
                method = iSelectorResult.getValue();
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

    private void log(final String name, final String value) {
        if (AssembledStepProcess.LOGGER.isDebugEnabled()) {
            AssembledStepProcess.LOGGER.debug(" > {}  {}", name, value);
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
        AssembledStepProcess.LOGGER.debug("URL >> " + result);
        return result;
    }
}
