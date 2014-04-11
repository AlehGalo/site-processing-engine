package com.jdev.collector.site;

import static com.jdev.crawler.core.AgentEnum.FIREFOX_USER_AGENT;
import static com.jdev.crawler.core.HttpClientFactory.createHttpClient;
import static com.jdev.crawler.core.HttpClientFactory.getCookieStore;
import static com.jdev.crawler.core.process.ProcessUtils.assemble;
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
import com.jdev.crawler.core.process.ProcessUtils;
import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.request.BasicRequestBuilder;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.jsoup.StringSourceJSoupSelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
import com.jdev.crawler.core.selector.regexp.ActionRegexpSelector;
import com.jdev.crawler.core.selector.simple.HostStaticStringSelector;
import com.jdev.crawler.core.selector.xpath.ActionXPathSelector;
import com.jdev.crawler.core.selector.xpath.XPathSelector;
import com.jdev.crawler.core.step.StepConfigAdapter;
import com.jdev.crawler.core.step.validator.SelectorValidator;
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
        process = ProcessUtils.chain(
                doGet("http://www.freelancer.com/jobs/"),
                /*
                 * assemble(new StepConfigAdapter() {
                 * 
                 * @Override public Collection<ISelector<?>> getParameters() {
                 * Collection<ISelector<?>> selectorCollection = new
                 * ArrayList<>(); selectorCollection .add(new
                 * ActionXPathSelector(
                 * "//div[@id='latest_jobs_table']//a[contains(text(), 'View All')]/@href"
                 * )); return selectorCollection; }
                 * 
                 * @Override public String getUrl() { return
                 * "http://www.freelancer.com/job/"; } }),
                 */doWhile(new ConditionalProcess(new SelectorValidator(new ActionRegexpSelector(
                // "//span[@id='project_table_next']/a/@href"
                        "<link rel=\"next\" href=\"(.*)\"/>")), chain(
                        ProcessUtils.skipKnownError(multi(new StepConfigAdapter() {
                            @Override
                            public Collection<ISelector<?>> getParameters() {
                                Collection<ISelector<?>> selectorCollection = new ArrayList<>();
                                selectorCollection
                                        .add(new ActionXPathSelector(
                                                "//table[@id='project_table_static']//tbody/tr[contains(@class,'project-details')]/td[1]/a/@href"));
                                return selectorCollection;
                            }
                        }, handler), "No HOST specified"), assemble(new StepConfigAdapter() {
                            @Override
                            public Collection<ISelector<?>> getParameters() {
                                Collection<ISelector<?>> collection = new ArrayList<>();
                                collection.add(new HostStaticStringSelector(
                                        "http://www.freelancer.com"));
                                collection.add(new ActionRegexpSelector(
                                        "<link rel=\"next\" href=\"(.*)\"/>"));
                                return collection;
                            }
                        })))));
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
                "//h1[contains(@class, 'project_name')]/text()"));

        /**
         * 
         */
        private final StringSourceJSoupSelector contentSelector = new StringSourceJSoupSelector(
                new SelectUnit("Content", "div#projectBrief div p"));

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
            new FreelancerComCollector().doIt();
        } catch (CrawlerException e) {
            e.printStackTrace();
        }
    }

}
