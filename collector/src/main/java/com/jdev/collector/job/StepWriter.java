/**
 * 
 */
package com.jdev.collector.job;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * @author Aleh
 * 
 */
public class StepWriter<T> implements ItemWriter<T> {

    @Override
    public void write(final List<? extends T> items) throws Exception {
        System.out.println(items);
    }

}
