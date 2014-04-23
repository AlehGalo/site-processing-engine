/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.domain.Job;

/**
 * @author Aleh
 * 
 */
public class JobDaoTest extends AbstractWriteDaoTest<Job> {

    @Override
    Job createEntity() {
        return JobUtils.createJob("ReasonNone");
    }

    @Override
    Job createUpdateEntity() {
        return JobUtils.createJob("ReasonNone1");
    }

}
