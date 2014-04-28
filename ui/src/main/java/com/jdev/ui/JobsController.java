/**
 * 
 */
package com.jdev.ui;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.jdev.domain.dao.IWriteDao;
import com.jdev.domain.domain.Job;

/**
 * @author Aleh
 * 
 */
// @Controller
public class JobsController {

    /**
     * 
     */
    // @Autowired
    private IWriteDao<Job> jobDao;

    // @RequestMapping(method = RequestMethod.GET, value = "/jobs")
    public ModelAndView get() {
        int count = (int) jobDao.countAll();
        List<Job> listOfJobs = jobDao.findAll(0, count);
        List<String> list = new LinkedList<>();
        for (Job job : listOfJobs) {
            list.add(job.getStartTime() + " - " + job.getEndTime() + ": " + job.getStatus() + " - "
                    + job.getReasonOfStopping());
        }
        ModelAndView modelAndView = new ModelAndView("results");
        modelAndView.addObject("count", count);
        modelAndView.addObject("lists", list);
        return modelAndView;
    }

}
