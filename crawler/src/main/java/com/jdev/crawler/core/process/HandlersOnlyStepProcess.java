/**
 * 
 */
package com.jdev.crawler.core.process;

import java.util.Arrays;
import java.util.List;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class HandlersOnlyStepProcess implements IProcess, IDescription {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private final List<IProcessResultHandler> handlersList;

    /**
     * 
     */
    private final String description;

    /**
     * @param handlers
     * @param config
     * @param description
     */
    public HandlersOnlyStepProcess(String description, IProcessResultHandler... handlers) {
        handlersList = Arrays.asList(handlers);
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public IEntity process(IProcessSession session, IEntity content,
            ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        for (IProcessResultHandler handler : handlersList) {
            handler.handle(session, content);
        }
        return content;
    }

}
