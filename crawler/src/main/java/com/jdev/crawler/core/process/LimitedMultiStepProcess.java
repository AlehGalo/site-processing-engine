/**
 * 
 */
package com.jdev.crawler.core.process;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.handler.SessionPopulateHandlerSelectionResult;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh <b>Requirement:</b> one and only 1 selector should contain more
 *         than 1 result. TODO: improve to any value of non unique selector
 *         results.
 */
public class LimitedMultiStepProcess extends AssembledStepProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedMultiStepProcess.class);

    /**
     * Step config.
     */
    private final IStepConfig config;

    /**
     * Multiple result list.
     */
    private final List<ISelectorResult> resultMultipleList;

    /**
     * Process.
     */
    private final IProcess process;

    /**
     * 
     */
    private final int limitForRequests;

    /**
     * 
     */
    private final RequestReservedWord word;

    /**
     * @param config
     * @param handlers
     * @param requestBuilder
     */
    public LimitedMultiStepProcess(final IStepConfig config,
            final List<IProcessResultHandler> handlers, final IRequestBuilder requestBuilder,
            final IProcess process, final int limit, final RequestReservedWord word) {
        super(config, handlers, requestBuilder);
        this.config = config;
        resultMultipleList = new ArrayList<ISelectorResult>();
        this.process = process;
        limitForRequests = limit;
        this.word = word;
    }

    /**
     * @param config
     * @param handlers
     * @param requestBuilder
     */
    public LimitedMultiStepProcess(final IStepConfig config,
            final List<IProcessResultHandler> handlers, final IRequestBuilder requestBuilder,
            final IProcess process, final int limit) {
        this(config, handlers, requestBuilder, process, limit, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.AbstractStepProcess#process(cinergy
     * .crawler .core.process.IProcessSession, byte[],
     * com.jdev.crawler.core.process.ISelectorExtractStrategy)
     */
    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy selectorExtractStrategy) throws CrawlerException {
        final Map<String, List<ISelectorResult>> allParameters = organizeParamsRequests(
                session.getSessionContext(), config, content, selectorExtractStrategy);
        checkMap(allParameters);
        final List<HttpRequestBase> listOfRequests = buildRequests(session.getSessionContext(),
                allParameters);
        Assert.isTrue(listOfRequests.size() == resultMultipleList.size(), MessageFormat.format(
                "Size of requests is [{0}] and size of multiple items is [{1}]",
                listOfRequests.size(), resultMultipleList.size()));
        int indexes = listOfRequests.size();
        if (limitForRequests > 0) {
            indexes = Math.min(indexes, limitForRequests);
        }
        for (int i = 0; i < indexes; i++) {
            try {
                if (word != null) {
                    final String name = word.getWord(), value = resultMultipleList.get(i)
                            .getValue();
                    final SessionPopulateHandlerSelectionResult populateHandler = new SessionPopulateHandlerSelectionResult(
                            new ISelectorResult() {

                                @Override
                                public String getName() {
                                    return name;
                                }

                                @Override
                                public String getValue() {
                                    return value;
                                }

                            });
                    populateHandler.handle(session, content);
                }
                final IEntity cont = handle(session,
                        executeRequest(session.getSessionContext(), listOfRequests.get(i)));
                if (process != null) {
                    process.process(session, cont, selectorExtractStrategy);
                }
            } catch (final IOException ex) {
                throw new CrawlerException(ex.getMessage(), ex);
            } catch (final InterruptedException ex) {
                throw new CrawlerException(ex.getMessage(), ex);
            }
        }
        return null;
    }

    /**
     * @param context
     *            process context.
     * @param config
     *            step config.
     * @param content
     *            binary content.
     * @param strategy
     *            {@link ISelectorExtractStrategy}
     * @return map associated with list of params.
     * @throws CrawlerException
     *             any kind of exception.
     */
    private Map<String, List<ISelectorResult>> organizeParamsRequests(
            final IProcessContext context, final IStepConfig config, final IEntity content,
            final ISelectorExtractStrategy strategy) throws CrawlerException {
        final List<ISelectorResult> list = strategy.extractSelectors(context, config, content);
        final Map<String, List<ISelectorResult>> resMap = new HashMap<String, List<ISelectorResult>>();
        for (final ISelectorResult i : list) {
            final String name = i.getName();
            List<ISelectorResult> valueList = resMap.get(name);
            if (valueList == null) {
                resMap.put(name, valueList = new ArrayList<ISelectorResult>());
            }
            valueList.add(i);
        }
        return resMap;
    }

    protected void checkMap(final Map<String, List<ISelectorResult>> map) throws SelectionException {
        if (map.isEmpty()) {
            throw new SelectionException("No data extracted for multiple step process.");
        }
        int counter = 0;
        for (final Map.Entry<String, List<ISelectorResult>> val : map.entrySet()) {
            final int size = val.getValue().size();
            if (size > 1) {
                ++counter;
            }
        }
        if (counter > 1) {
            throw new SelectionException(
                    MessageFormat.format(
                            "Too much elements for multiple step. Number of plural elements [{0}]",
                            counter));
        }
    }

    /**
     * @param context
     * @param map
     * @return
     * @throws CrawlerException
     */
    private List<HttpRequestBase> buildRequests(final IProcessContext context,
            final Map<String, List<ISelectorResult>> map) throws CrawlerException {
        if (map != null) {
            String key = null;
            for (final Map.Entry<String, List<ISelectorResult>> val : map.entrySet()) {
                if (val.getValue().size() > 1) {
                    key = val.getKey();
                    break;
                }
            }
            final List<ISelectorResult> valuesList = map.get(key);
            map.remove(key);
            final String keyFinal = key;
            final List<HttpRequestBase> result = new ArrayList<HttpRequestBase>();
            resultMultipleList.clear();
            for (final ISelectorResult selRes : valuesList) {
                final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
                resultList.addAll(getValuesAsListFromMap(map));
                resultMultipleList.add(selRes);
                resultList.add(selRes);
                result.add(createRequest(context, resultList));
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Multi item selection name=[{}] value=[{}]", keyFinal,
                            selRes.getValue());
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Number of multi requests [{}/{}]", limitForRequests, result.size());
            }
            return result;
        }
        return new ArrayList<HttpRequestBase>();
    }

    /**
     * @param map
     * @return
     */
    private List<ISelectorResult> getValuesAsListFromMap(
            final Map<String, List<ISelectorResult>> map) {
        final List<ISelectorResult> list = new ArrayList<ISelectorResult>();
        if (map != null) {
            for (final List<ISelectorResult> iSelectorResult : map.values()) {
                list.addAll(iSelectorResult);
            }
        }
        return list;
    }
}