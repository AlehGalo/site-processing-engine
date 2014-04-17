/**
 * 
 */
package com.jdev.collector.site.handler;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Element;

import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.jsoup.StringSourceJSoupSelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.core.selector.xpath.XPathSelector;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.domain.domain.Article;

/**
 * @author Aleh
 * 
 */
public class FreelanceComHandler extends ArticleWatcher implements IProcessResultHandler {

    /**
     * 
     */
    private final ISelector<String> titleSelector = new XPathSelector(new SelectUnit("Headers",
            "normalize-space(//span[contains(@class,'highlightable')]/text())"));

    /**
     * 
     */
    private final StringSourceJSoupSelector contentSelector = new StringSourceJSoupSelector(
            new SelectUnit("Content", "div.wysiwygWrapper"));

    /**
     * 
     */
    public FreelanceComHandler() {
        contentSelector.setExtractor(new IJSoupElementExtractor() {

            @Override
            public String getValueFromRecord(final Element element) {
                return element.text();
            }
        });
    }

    @Override
    public void handle(final IProcessSession session, final IEntity entity) throws CrawlerException {
        String content = new String(entity.getContent(), entity.getCharset());
        Article article = new Article((String) CollectionUtils.get(contentSelector.select(content),
                0));
        article.setTitle((String) CollectionUtils.get(titleSelector.select(content), 0));
        setArticle(article);
        notifyListeners();
    }
}
