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
import com.jdev.domain.domain.Article;

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
    private IWriteDao<Article> articleReadDao;

    @RequestMapping(method = RequestMethod.GET, value = "/results")
    public ModelAndView get() {
        List<Article> listOfArticles = articleReadDao.findAll(0, (int) articleReadDao.countAll());
        List<String> list = new LinkedList<>();
        for (Article article : listOfArticles) {
            list.add(article.getTitle());
        }
        ModelAndView modelAndView = new ModelAndView("results");
        modelAndView.addObject("lists", list);
        return modelAndView;
    }
}
