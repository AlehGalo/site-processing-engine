/**
 * 
 */
package com.jdev.crawler.core.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 * 
 *         This step process optional step. Pages that can sometimes appear but
 *         sometimes are missed. In the case of absence of the page nothing will
 *         be processed and content will be directed to the next step.
 */
class OptionalAssembledStepProcessor extends AssembledStepProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(OptionalAssembledStepProcessor.class);

    /**
     * 
     */
    private final IStepConfig config;

    /**
     * @param handlers
     * @param config
     */
    public OptionalAssembledStepProcessor(final IStepConfig config,
            final List<IProcessResultHandler> handlers) {
        super(config, handlers);
        this.config = config;
    }

    @Override
    public byte[] process(final IProcessSession session, final byte[] content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        try {
            extractStrategy.extractSelectors(session.getSessionContext(), config, content);
            return super.process(session, content, extractStrategy);
        } catch (SelectionException se) {
            LOGGER.error("Page seems to be missed. As it's an optional processor - will skip it."
                    + se.getMessage());
            return content;
        }
    }
}
