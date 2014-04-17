/**
 * 
 */
package com.jdev.collector.site.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Element;

import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.jsoup.StringSourceJSoupSelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.core.selector.xpath.XPathSelector;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class FreelancerComHandler implements IProcessResultHandler {

    /**
     * 
     */
    final AtomicInteger counter = new AtomicInteger(1);

    /**
     * 
     */
    long timer = System.currentTimeMillis();

    /**
     * 
     */
    private final ISelector<String> titleSelector = new XPathSelector(new SelectUnit("Headers",
            "//h1[contains(@class, 'project_name')]/text()"));

    /**
     * 
     */
    private final StringSourceJSoupSelector contentSelector = new StringSourceJSoupSelector(
            new SelectUnit("Content", "div#projectBrief div p"));

    /**
     * 
     */
    public FreelancerComHandler() {
        contentSelector.setExtractor(new IJSoupElementExtractor() {

            @Override
            public String getValueFromRecord(final Element element) {
                return element.text();
            }
        });

    }

    @Override
    public void handle(final IProcessSession session, final IEntity entity) throws CrawlerException {
        // TODO: change it
        long time = System.currentTimeMillis() - timer;
        String content = new String(entity.getContent(), entity.getCharset());
        printValueOfIselectorResult(CollectionUtils.get(titleSelector.select(content), 0));
        printValueOfIselectorResult(CollectionUtils.get(contentSelector.select(content), 0));
        System.out.println(time + " ms. Number of records: " + counter.getAndAdd(1));
        timer = System.currentTimeMillis();
    }

    /**
     * @param obj
     */
    private void printValueOfIselectorResult(final Object obj) {
        if (obj instanceof ISelectorResult) {
            ISelectorResult result = (ISelectorResult) obj;
            System.out.println(result.getValue());
        }
    }

}
