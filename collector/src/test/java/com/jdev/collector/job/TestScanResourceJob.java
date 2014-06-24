/**
 * 
 */
package com.jdev.collector.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Aleh
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:com/jdev/domain/data/daoSpringApplicationContext.xml",
        "classpath:com/jdev/collector/site/collectorSpringContext.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestScanResourceJob {

    @Test
    public void test1() {

    }
}
