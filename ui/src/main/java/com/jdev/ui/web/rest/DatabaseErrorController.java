/**
 * 
 */
package com.jdev.ui.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.entity.DatabaseError;

/**
 * @author Aleh
 * 
 */
@RestController
public class DatabaseErrorController {

    /**
     * 
     */
    @Autowired
    private IReadDao<DatabaseError> databaseErrorReadDao;

    @RequestMapping(method = RequestMethod.GET, value = "/dberror/{jobId}")
    public ModelAndView get(@PathVariable(value = "jobId") int jobId) {
        List<DatabaseError> listOfDatabaseErrors = databaseErrorReadDao.findAll();
        // TODO: implement
        ModelAndView modelAndView = new ModelAndView("dberrors");
        return modelAndView;
    }

}
