package com.jdev.crawler.core.process;

import com.jdev.crawler.core.process.model.IEntity;

/**
 *
 */
public interface IConditionalProcess extends IProcess {

    /**
     * @param content
     *            binary stream for processing.
     * @return true/false.
     */
    boolean match(IEntity content);

}
