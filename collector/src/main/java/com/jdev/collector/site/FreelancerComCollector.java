package com.jdev.collector.site;

import static com.jdev.crawler.core.AgentEnum.FIREFOX_USER_AGENT;
import static com.jdev.crawler.core.HttpClientFactory.createHttpClient;
import static com.jdev.crawler.core.HttpClientFactory.getCookieStore;
import static com.jdev.crawler.core.process.ProcessUtils.chain;
import static com.jdev.crawler.core.process.ProcessUtils.doGet;
import static com.jdev.crawler.core.process.ProcessUtils.doWhile;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Element;

import com.jdev.crawler.builder.CrawlerBuilder;
import com.jdev.crawler.core.ICrawler;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.request.BasicRequestBuilder;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.jsoup.StringSourceJSoupSelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.core.selector.xpath.XPathSelector;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class FreelancerComCollector {

    private IProcess process;

    private UserData userData;

    private ICrawler crawler;

    public FreelancerComCollector() {
        userData = new UserData("informer-freelancer-com", "EMPTY");
        userData.setCompany(new ICompany() {

            @Override
            public String getCompanyName() {
                return "freelancer_com";
            }

            @Override
            public Integer getCompanyId() {
                return 3;
            }
        });
        LocalProcessResultHandler handler = new LocalProcessResultHandler();
        process = chain(doGet("http://www.freelancer.com/job/"), doWhile(null));

        crawler = new CrawlerBuilder(process, userData)
                .buildClient(createHttpClient(FIREFOX_USER_AGENT))
                .buildCookieStore(getCookieStore()).buildRequestBuilder(new BasicRequestBuilder())
                .getResult();
    }

    private class LocalProcessResultHandler implements IProcessResultHandler {
        final AtomicInteger counter = new AtomicInteger(1);
        long timer = System.currentTimeMillis();

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
        public LocalProcessResultHandler() {
            contentSelector.setExtractor(new IJSoupElementExtractor() {

                @Override
                public String getValueFromRecord(final Element element) {
                    return element.text();
                }
            });

        }

        @Override
        public void handle(final IProcessSession session, final IEntity entity)
                throws CrawlerException {
            // TODO: change it
            long time = System.currentTimeMillis() - timer;
            String content = new String(entity.getContent(), entity.getCharset());
            contentSelector.select(content);
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

    public void doIt() throws CrawlerException {
        crawler.collect();
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        try {
            new FreelanceComCollector().doIt();
        } catch (CrawlerException e) {
            e.printStackTrace();
        }
    }

}
