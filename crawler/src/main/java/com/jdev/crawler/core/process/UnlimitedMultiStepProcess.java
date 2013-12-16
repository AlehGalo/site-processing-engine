/**
 * 
 */
package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.step.IStepConfig;

/**
 * @author Aleh
 * 
 */
public class UnlimitedMultiStepProcess extends LimitedMultiStepProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param config
     * @param handlers
     * @param populator
     * @param process
     */
    public UnlimitedMultiStepProcess(IStepConfig config, List<IProcessResultHandler> handlers,
            IProcess process, RequestReservedWord word) {
        super(config, handlers, null, process, -1, word);
    }

    /**
     * @param config
     * @param handlers
     * @param process
     */
    public UnlimitedMultiStepProcess(IStepConfig config, List<IProcessResultHandler> handlers,
            IProcess process) {
        super(config, handlers, null, process, -1, null);
    }
}
