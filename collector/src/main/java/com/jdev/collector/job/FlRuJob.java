/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

/**
 * @author Aleh
 * 
 */
@Service
@EnableAsync
@EnableScheduling
public class FlRuJob extends AbstractScanResourceJob {

    /**
     * 
     */
    public FlRuJob() {
        super("informer-fl-ru");
    }
}