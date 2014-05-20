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
        Job job = EntityUtils.createJob("ReasonNone");
        return job;
    }

    @Override
    Job createUpdateEntity() {
        Job job = EntityUtils.createJob("ReasonNone1");
        return job;
    }

}
