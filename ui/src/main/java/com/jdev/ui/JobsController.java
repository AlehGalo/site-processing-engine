/**
 * 
 */
package com.jdev.ui;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Job;

/**
 * @author Aleh
 * 
 */
@Controller
public class JobsController {

    /**
     * 
     */
    @Autowired
    private IWriteDao<Job> jobDao;

    @RequestMapping(method = RequestMethod.GET, value = "/jobs")
    public ModelAndView get() {
        List<Job> listOfJobs = jobDao.findAll();
        List<String> list = new LinkedList<>();
        for (Job job : listOfJobs) {
            list.add(job.getCredential().getSite().getUrl() + " " + job.getStartTime() + " - "
                    + job.getEndTime() + ": crawler errors " + job.getCrawlerErrorsCount()
                    + ", database errors " + job.getDatabaseErrorsCount() + " status "
                    + job.getStatus() + " cause " + job.getReasonOfStopping());
        }
        ModelAndView modelAndView = new ModelAndView("results");
        modelAndView.addObject("count", listOfJobs.size());
        modelAndView.addObject("lists", list);
        return modelAndView;
    }

}
