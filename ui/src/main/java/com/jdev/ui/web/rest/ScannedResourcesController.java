/**
 * 
 */
package com.jdev.ui.web.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jdev.domain.dao.IReadDao;
import com.jdev.domain.dto.ArticleDto;
import com.jdev.domain.entity.Article;
import com.jdev.domain.entity.Article_;

/**
 * @author Aleh
 * 
 */
@RestController
public class ScannedResourcesController {

    /**
     * 
     */
    @Autowired
    private IReadDao<Article> articleDao;

    @RequestMapping(method = RequestMethod.GET, value = "/results")
    public ModelAndView get() {
        List<Article> listOfArticles = articleDao.findFields(Arrays.asList(
                Article_.originalArticleUrl, Article_.title));
        List<ArticleDto> articleDtoList = new ArrayList<>();
        for (Article article : listOfArticles) {
            articleDtoList.add(new ArticleDto(article.getOriginalArticleUrl(), article.getTitle(),
                    ""));
        }
        ModelAndView modelAndView = new ModelAndView("results");
        modelAndView.addObject("count", listOfArticles.size());
        modelAndView.addObject("lists", articleDtoList);
        return modelAndView;
    }
}
