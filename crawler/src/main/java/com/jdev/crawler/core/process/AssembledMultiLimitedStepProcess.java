/**
 * 
 */
package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.IStepConfig;

/**
 * @author Aleh
 * 
 */
public class AssembledMultiLimitedStepProcess extends AssembledMultiStepProcess {

    /**
     * Default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private final int limit;

    /**
     * @param config
     * @param handlers
     * @param requestBuilder
     * @param limit
     */
    public AssembledMultiLimitedStepProcess(final IStepConfig config,
            final List<IProcessResultHandler> handlers, final IRequestBuilder requestBuilder,
            final int limit) {
        super(config, handlers, requestBuilder);
        this.limit = limit;
    }

    /**
     * @param config
     * @param handlers
     * @param limit
     */
    public AssembledMultiLimitedStepProcess(final IStepConfig config,
            final List<IProcessResultHandler> handlers, final int limit) {
        super(config, handlers);
        this.limit = limit;
    }

    @Override
    protected GroupOfResults createGroupOfResults(final List<ISelectorResult> list) {
        GroupOfResults groupOfResults = new GroupOfResults();
        GroupOfResults parentResult = super.createGroupOfResults(list);
        int i = 0;
        for (List<ISelectorResult> parent : parentResult) {
            if (i > limit) {
                break;
            }
            groupOfResults.add(parent);
            ++i;
        }
        return groupOfResults;
    }

}
