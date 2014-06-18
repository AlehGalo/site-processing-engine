/**
 * 
 */
package com.jdev.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.jdev.domain.dao.CrawlerErrorDaoTest;
import com.jdev.domain.dao.DatabaseErrorDaoTest;

/**
 * @author Aleh
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ DatabaseErrorDaoTest.class, CrawlerErrorDaoTest.class })
public class ProblemTest {

}
