/**
 * 
 */
package com.jdev.ui.web.rest;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.dto.JobDto;
import com.jdev.domain.entity.Job;

@RestController
@RequestMapping("/app")
public class JobsController {

    /**
     * 
     */
    @Autowired
    private IReadDao<Job> jobDao;

    @RequestMapping(method = RequestMethod.GET, value = "/jobs")
    public ModelAndView get() {
        List<Job> listOfJobs = jobDao.findAll();
        List<JobDto> list = new LinkedList<>();
        for (Job job : listOfJobs) {
            JobDto jobDto = new JobDto(job.getStartTime(), job.getEndTime(), job.getStatus(), job
                    .getCredential().getSite().getUrl());
            jobDto.setCrawlerErrorCount(job.getCrawlerErrorsCount());
            jobDto.setDatabaseErrorCount(job.getDatabaseErrorsCount());
            jobDto.setArticlesCollected(job.getRecordsCount());
            list.add(jobDto);
        }
        ModelAndView modelAndView = new ModelAndView("jobs");
        modelAndView.addObject("count", listOfJobs.size());
        modelAndView.addObject("lists", list);
        return modelAndView;
    }
}