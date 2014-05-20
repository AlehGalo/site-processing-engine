/**
 * 
 */
package com.jdev.crawler.core.process;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;

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
     * @param config
     */
    public OptionalAssembledStepProcessor(final IStepConfig config) {
        this(config, null);
    }

    /**
     * @param handlers
     * @param config
     */
    public OptionalAssembledStepProcessor(final IStepConfig config,
            final IProcessResultHandler handler) {
        super(config, handler);
        this.config = config;
    }

    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        if (isEmpty(extractStrategy.extractSelectors(session.getSessionContext(), config, content))) {
            LOGGER.info("Page seems to be missed. As it's an optional processor - will skip it.");
            return content;
        }
        return super.process(session, content, extractStrategy);
    }
}
