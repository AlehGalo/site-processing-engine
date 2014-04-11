package com.jdev.collector.site;

import static com.jdev.crawler.core.AgentEnum.FIREFOX_USER_AGENT;
import static com.jdev.crawler.core.HttpClientFactory.createHttpClient;
import static com.jdev.crawler.core.HttpClientFactory.getCookieStore;
import static com.jdev.crawler.core.process.ProcessUtils.chain;
import static com.jdev.crawler.core.process.ProcessUtils.doGet;
import static com.jdev.crawler.core.process.ProcessUtils.doWhile;
import static com.jdev.crawler.core.process.ProcessUtils.multi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Element;

import com.jdev.crawler.builder.CrawlerBuilder;
import com.jdev.crawler.core.ICrawler;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.request.BasicRequestBuilder;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.jsoup.StringSourceJSoupSelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.core.selector.simple.HostStaticStringSelector;
import com.jdev.crawler.core.selector.xpath.ActionXPathSelector;
import com.jdev.crawler.core.selector.xpath.XPathSelector;
import com.jdev.crawler.core.step.StepConfigAdapter;
import com.jdev.crawler.core.step.validator.SelectorValidator;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh http://www.freelance.com/
 */
public class FreelanceComCollector {

    // informer-fl-com
    // aFSsSR5435

    // Mail account
    // informer.email@yandex.ru
    // AdfdGG#r%$#@$55345

    private IProcess process;

    private UserData userData;

    private ICrawler crawler;

    public FreelanceComCollector() {
        userData = new UserData("informer-fl-com", "aFSsSR5435");
        userData.setCompany(new ICompany() {

            @Override
            public String getCompanyName() {
                return "freelance_com";
            }

            @Override
            public Integer getCompanyId() {
                return 2;
            }
        });
        LocalProcessResultHandler handler = new LocalProcessResultHandler();
        process = chain(doGet("http://www.freelance.com/en/search/mission"),
                doWhile(new ConditionalProcess(new SelectorValidator(new ActionXPathSelector(
                        "//a[contains(text(), 'Next')]/@href")), multi(new StepConfigAdapter() {
                    @Override
                    public Collection<ISelector<?>> getParameters() {
                        Collection<com.jdev.crawler.core.selector.ISelector<?>> parameters = new ArrayList<>();
                        parameters.add(new HostStaticStringSelector("http://www.freelance.com"));
                        parameters.add(new ActionXPathSelector(
                                "//table[@id='result']/tbody//a/@href"));
                        return parameters;
                    }
                }, handler))));

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

    public void doIt() throws CrawlerException {
        crawler.collect();
        System.out.println("Done");
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
