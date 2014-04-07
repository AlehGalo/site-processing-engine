/**
 * 
 */
package com.jdev.crawler.core.process;

import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.step.validator.IValidator;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class EmptyConditionalProcess extends ConditionalProcess {

    /**
     * @param validator
     * @param inner
     */
    public EmptyConditionalProcess(IValidator validator) {
        super(validator, null);
    }

    @Override
    public IEntity process(IProcessSession session, IEntity content,
            ISelectorExtractStrategy extractStrategy) throws CrawlerException {
        return content;
    }
}
