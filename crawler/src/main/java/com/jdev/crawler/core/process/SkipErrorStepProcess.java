/**
 * 
 */
package com.jdev.crawler.core.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class SkipErrorStepProcess implements IProcess, IDescription {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SkipErrorStepProcess.class);

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Process.
     */
    private final IProcess process;

    /**
     * 
     */
    private final String message;

    /**
     * @param process
     * @param message
     */
    public SkipErrorStepProcess(final IProcess process, final String message) {
        Assert.notNull(process);
        Assert.notNull(message);
        this.process = process;
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IDescription#getDescription()
     */
    @Override
    public String getDescription() {
        return "SkipErrorStepProcess";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.IProcess#process(com.jdev.crawler.core.
     * process.IProcessSession, com.jdev.crawler.core.process.model.IEntity,
     * com.jdev.crawler.core.process.extract.ISelectorExtractStrategy)
     */
    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        try {
            return process.process(session, content, extractStrategy);
        } catch (Exception e) {
            LOGGER.info(message, e);
        }
        return null;
    }

}
