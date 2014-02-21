package com.jdev.crawler.core.process.extract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jdev.crawler.core.process.IProcessContext;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh Simple extract all selectors and put result into collection.
 */
public class DefaultSelectorExtractStrategy implements ISelectorExtractStrategy {

    /**
     * The point where decition of content input is taken.
     */
    private SelectrosExtractStrategyAdapter adapter;

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.extract.ISelectorExtractStrategy#
     * extractSelectors(com.jdev.crawler.core.process.IProcessContext,
     * com.jdev.crawler.core.step.IStepConfig, byte[])
     */
    @Override
    public List<ISelectorResult> extractSelectors(final IProcessContext context,
            final IStepConfig config, final byte[] content) throws SelectionException {
        final List<ISelectorResult> resultList = new ArrayList<ISelectorResult>();
        if (content != null) {
            final Collection<ISelector<?>> selectorsList = config.getParameters();
            if (!CollectionUtils.isEmpty(selectorsList)) {
                for (final ISelector<?> iSelector : selectorsList) {
                    resultList.addAll(adapter.extractSelectors(iSelector, context, content));
                }
            }
        }
        return resultList;
    }
}