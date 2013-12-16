package com.jdev.crawler.core.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 * 
 */
public class MergingSelectorExtractStrategy extends
	DefaultSelectorExtractStrategy {
    private final List<? extends ISelectorResult> items;

    /**
     * @param items
     */
    public MergingSelectorExtractStrategy(final ISelectorResult... items) {
        this.items = Arrays.asList(items);
    }

    @Override
    public List<ISelectorResult> extractSelectors(
	    final IProcessContext context, final IStepConfig config,
	    final byte[] content) throws SelectionException {
        final List<ISelectorResult> results = new ArrayList<ISelectorResult>();
        results.addAll(super.extractSelectors(context, config, content));
        results.addAll(items);
        return results;
    }
}
