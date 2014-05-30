/**
 * 
 */
package com.jdev.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.domain.Article;
import com.jdev.domain.domain.Article_;

/**
 * @author Aleh
 * 
 */
@Controller
public class ScannedResourcesController {

    /**
     * 
     */
    @Autowired
    private IReadDao<Article> articleDao;

    @RequestMapping(method = RequestMethod.GET, value = "/results")
    public ModelAndView get() {
        List<String> listOfArticles = articleDao.find(Article_.title);
        ModelAndView modelAndView = new ModelAndView("results");
        modelAndView.addObject("count", listOfArticles.size());
        modelAndView.addObject("lists", listOfArticles);
        return modelAndView;
    }
}
