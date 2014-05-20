/**
 * 
 */
package com.jdev.collector.site.handler;

import org.jsoup.nodes.Element;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.jsoup.StringSourceJSoupSelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.core.selector.xpath.XPathSelector;

/**
 * @author Aleh
 * 
 */
public class FreelancerComHandler extends ArticleWatcher {

    /**
     * 
     */
    private static final ISelector<String> titleSelector = new XPathSelector(new SelectUnit(
            "Headers", "//h1[contains(@class, 'project_name')]/text()"));

    /**
     * 
     */
    private static final StringSourceJSoupSelector contentSelector = new StringSourceJSoupSelector(
            new SelectUnit("Content", "div#projectBrief div p"));

    static {
        contentSelector.setExtractor(new IJSoupElementExtractor() {

            @Override
            public String getValueFromRecord(final Element element) {
                return element.text();
            }
        });
    }

    /**
     * 
     */
    public FreelancerComHandler() {
        super(contentSelector, titleSelector);
    }

}
