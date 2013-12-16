package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.SelectionException;

public interface ISelectorExtractStrategy {
    List<ISelectorResult> extractSelectors(
            IProcessContext context, IStepConfig config, byte[] content)
            throws SelectionException;
}
