package com.jdev.crawler.core.process.extract;

import java.util.List;

import com.jdev.crawler.core.process.IProcessContext;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh Main strategy of extracting all of the selectors.
 */
public interface ISelectorExtractStrategy {

    /**
     * @param context
     * @param config
     * @param content
     * @return
     * @throws SelectionException
     */
    List<ISelectorResult> extractSelectors(IProcessContext context, IStepConfig config,
            IEntity content) throws SelectionException;
}
