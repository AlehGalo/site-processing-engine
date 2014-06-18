/**
 * 
 */
package com.jdev.collector.job.handler;

import org.hibernate.exception.ConstraintViolationException;

import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class IncreaseErrorCounterExceptionalHandler<T extends Throwable> implements
        IExceptionalCaseHandler<T> {

    @Override
    public boolean handle(T t) {
        if (t instanceof CrawlerException || t instanceof ConstraintViolationException) {
            increaseErrors();
            return true;
        }
        return false;
    }

    public void increaseErrors() {
    }

}