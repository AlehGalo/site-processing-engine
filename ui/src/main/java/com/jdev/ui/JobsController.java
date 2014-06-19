/**
 * 
 */
package com.jdev.ui;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.dao.criteria.ICriteriaComposer;
import com.jdev.domain.dto.JobDto;
import com.jdev.domain.entity.CrawlerError;
import com.jdev.domain.entity.Job;

@RestController
public class JobsController {

    /**
     * 
     */
    @Autowired
    private IReadDao<Job> jobDao;

    /**
     * 
     */
    @Autowired
    private IReadDao<CrawlerError> readCrawlerErrorDao;

    @RequestMapping(method = RequestMethod.GET, value = "/jobs")
    public ModelAndView get() {
        List<Job> listOfJobs = jobDao.findAll();

        List<JobDto> list = new LinkedList<>();
        for (Job job : listOfJobs) {
            list.add(new JobDto(job.getStartTime(), job.getEndTime(), job.getStatus(), job
                    .getCredential().getSite().getUrl()));
        }
        ModelAndView modelAndView = new ModelAndView("jobs");
        modelAndView.addObject("count", listOfJobs.size());
        modelAndView.addObject("lists", list);
        return modelAndView;
    }

    public void getCount() {
        ICriteriaComposer<CrawlerError> criteriaComposer = readCrawlerErrorDao
                .getCriteriaComposer();
    }
}
