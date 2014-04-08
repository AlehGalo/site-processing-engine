package com.jdev.crawler.core.process.extract;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.jdev.crawler.core.process.IProcessContext;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.selector.SelectorResult;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh Simple extract all selectors and put result into collection.
 */
public class DefaultSelectorExtractStrategy implements ISelectorExtractStrategy {

    /**
     * The point where decition of content input is taken.
     */
    private final SelectrosExtractStrategyAdapter adapter = new SelectrosExtractStrategyAdapter();

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.extract.ISelectorExtractStrategy#
     * extractSelectors(com.jdev.crawler.core.process.IProcessContext,
     * com.jdev.crawler.core.step.IStepConfig, byte[])
     */
    @Override
    public List<ISelectorResult> extractSelectors(final IProcessContext context,
            final IStepConfig config, final IEntity content) throws SelectionException {
        final List<ISelectorResult> list = new ArrayList<ISelectorResult>();
        if (content != null) {
            final Collection<ISelector<?>> selectorsList = config.getParameters();
            Collection<ISelectorResult> collection = populateHostMethod(config);
            if (collection != null) {
                list.addAll(collection);
            }
            if (!CollectionUtils.isEmpty(selectorsList)) {
                for (final ISelector<?> iSelector : selectorsList) {
                    list.addAll(adapter.extractSelectors(iSelector, context, content));
                }
            }
        }
        return list;
    }

    /**
     * Populate HOST and METHOD from cofig if URL and METHOD are created. If
     * it's overridden in parameters then parameters will take effect.
     * 
     * @param collection
     *            of selectors.
     * @return collection clean of HOST, METHOD if it has been already defined.
     */
    protected Set<ISelectorResult> populateHostMethod(final IStepConfig config) {
        Set<ISelectorResult> set = new HashSet<>();
        if (config != null) {
            populateSelectorResult(RequestReservedWord.METHOD, config.getMethod().name(), set);
            populateSelectorResult(RequestReservedWord.HOST, config.getUrl(), set);
        }
        return set;
    }

    /**
     * 
     * Populate SelectorResult value into the list.
     * 
     * @param word
     *            enum value.
     * @param value
     *            to be populated.
     * @param set
     *            to be processed with.
     */
    private void populateSelectorResult(final RequestReservedWord word, final String value,
            final Set<ISelectorResult> set) {
        if (isBlank(value) || word == null || set == null) {
            return;
        }
        set.add(new SelectorResult(word.getWord(), value));
    }
}