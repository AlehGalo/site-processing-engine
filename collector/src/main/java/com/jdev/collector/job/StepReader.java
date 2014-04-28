/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * @author Aleh
 * 
 */
public class StepReader<T> implements ItemReader<T> {

    private final T t;

    public StepReader(final T t) {
        this.t = t;
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException,
            NonTransientResourceException {
        System.out.println(t);
        return null;
    }

}
