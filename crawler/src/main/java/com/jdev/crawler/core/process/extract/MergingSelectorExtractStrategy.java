package com.jdev.crawler.core.process.extract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jdev.crawler.core.process.IProcessContext;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh Strategy that is using @see
 *         {@link DefaultSelectorExtractStrategy} and add other items to the
 *         results list.
 */
public class MergingSelectorExtractStrategy extends DefaultSelectorExtractStrategy {

    /**
     * Result list of selectors.
     */
    private final List<? extends ISelectorResult> items;

    /**
     * @param items
     */
    public MergingSelectorExtractStrategy(final ISelectorResult... items) {
        this.items = Arrays.asList(items);
    }

    @Override
    public List<ISelectorResult> extractSelectors(final IProcessContext context,
            final IStepConfig config, final IEntity content) throws SelectionException {
        final List<ISelectorResult> results = new ArrayList<ISelectorResult>();
        results.addAll(super.extractSelectors(context, config, content));
        results.addAll(items);
        return results;
    }
}
