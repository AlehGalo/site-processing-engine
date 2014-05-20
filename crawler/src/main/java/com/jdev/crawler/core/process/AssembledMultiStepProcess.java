/**
 * 
 */
package com.jdev.crawler.core.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 */
public class AssembledMultiStepProcess extends AssembledStepProcess {

    /**
     * Default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AssembledMultiStepProcess.class);

    /**
     * @param config
     * @param handler
     * @param requestBuilder
     */
    public AssembledMultiStepProcess(final IStepConfig config,
            final IProcessResultHandler handlers, final IRequestBuilder requestBuilder) {
        super(config, handlers, requestBuilder);
    }

    /**
     * @param config
     * @param handlers
     */
    public AssembledMultiStepProcess(final IStepConfig config, final IProcessResultHandler handler) {
        super(config, handler);
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
        try {
            IProcessContext context = session.getSessionContext();
            List<ISelectorResult> selectorResult = extractSelectors(context,
                    selectorExtractStrategy, content);
            GroupOfResults group = createGroupOfResults(selectorResult);
            for (List<ISelectorResult> list : group) {
                HttpRequestBase request = createRequest(context, list);
                IEntity result = executeRequest(context, request);
                checkIEntityProperties(result);
                handle(session, result);
            }
        } catch (final IOException | InterruptedException ex) {
            throw new CrawlerException(ex.getMessage(), ex);
        }
        return content;
    }

    /**
     * @author Aleh
     * 
     */
    protected final class GroupOfResults implements Iterable<List<ISelectorResult>> {

        /**
         * 
         */
        private final List<List<ISelectorResult>> selectionResults;

        /**
         * 
         */
        GroupOfResults() {
            selectionResults = new ArrayList<>();
        }

        /**
         * @param result
         */
        void add(final List<ISelectorResult> result) {
            if (CollectionUtils.isNotEmpty(result)) {
                selectionResults.add(result);
            }
        }

        @Override
        public Iterator<List<ISelectorResult>> iterator() {
            return selectionResults.iterator();
        }

    }

    /**
     * @param list
     * @return
     */
    protected GroupOfResults createGroupOfResults(final List<ISelectorResult> list) {
        GroupOfResults result = new GroupOfResults();
        Map<String, ISelectorResult> storage = new HashMap<>();
        Set<String> duplicateNames = new HashSet<>();
        List<ISelectorResult> listOfDuplicates = new ArrayList<>();
        for (ISelectorResult iSelectorResult : list) {
            String name = iSelectorResult.getName();
            if (duplicateNames.contains(name)) {
                listOfDuplicates.add(iSelectorResult);
                debugISelectorResult(iSelectorResult, "Duplicate result found name={}, value={}");
            } else if (storage.containsKey(name)) {
                listOfDuplicates.add(iSelectorResult);
                listOfDuplicates.add(storage.remove(name));
                duplicateNames.add(name);
                debugISelectorResult(iSelectorResult,
                        "Duplicate result found name={}, value={} moved value from storage map");
            } else {
                storage.put(name, iSelectorResult);
                debugISelectorResult(iSelectorResult, "Selector result found name={}, value={}");
            }
        }
        for (ISelectorResult iSelectorResult : listOfDuplicates) {
            List<ISelectorResult> listOfSelectorResult = new ArrayList<>();
            listOfSelectorResult.addAll(storage.values());
            listOfSelectorResult.add(iSelectorResult);
            result.add(listOfSelectorResult);
        }
        return result;
    }

    /**
     * @param iSelectorResult
     * @param message
     */
    private void debugISelectorResult(final ISelectorResult iSelectorResult, final String message) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(message, iSelectorResult.getName(), iSelectorResult.getValue());
        }
    }
}