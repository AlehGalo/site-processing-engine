package com.jdev.crawler.core.process;

/**
 *
 */
public interface IConditionalProcess extends IProcess {

    /**
     * @param content
     *            binary stream for processing.
     * @return true/false.
     */
    boolean match(byte[] content);

}
