/**
 * 
 */
package com.jdev.collector.site;

import static com.jdev.crawler.core.AgentEnum.FIREFOX_USER_AGENT;
import static com.jdev.crawler.core.HttpClientFactory.createHttpClient;
import static com.jdev.crawler.core.HttpClientFactory.getCookieStore;
import static com.jdev.crawler.core.process.ProcessUtils.assemble;
import static com.jdev.crawler.core.process.ProcessUtils.chain;
import static com.jdev.crawler.core.process.ProcessUtils.doGet;
import static com.jdev.crawler.core.process.ProcessUtils.doWhile;
import static com.jdev.crawler.core.process.ProcessUtils.forEach;
import static com.jdev.crawler.core.process.ProcessUtils.handlersOnly;
import static com.jdev.crawler.core.process.ProcessUtils.waitUntilValidatoIsTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.jdev.crawler.builder.CrawlerBuilder;
import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.process.handler.SessionPopulateHandler;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.request.BasicRequestBuilder;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.regexp.ActionRegexpSelector;
import com.jdev.crawler.core.selector.regexp.RegexpSelector;
import com.jdev.crawler.core.selector.simple.HostStaticStringSelector;
import com.jdev.crawler.core.selector.simple.StaticStringSelector;
import com.jdev.crawler.core.selector.xpath.ActionXPathSelector;
import com.jdev.crawler.core.selector.xpath.XPathSelector;
import com.jdev.crawler.core.step.HTTPMethod;
import com.jdev.crawler.core.step.StepConfigAdapter;
import com.jdev.crawler.core.step.validator.SelectorValidator;
import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public class FLRUCollector {

    // Mail account
    // informer.email@yandex.ru
    // AdfdGG#r%$#@$55345

    // informer-fl-ru
    // aFGgR5435

    /**
     * 
     */
    public FLRUCollector() {
    }

    // action:login
    // login:informer-fl-ru
    // passwd:aFGgR5435

    public static void main(final String args[]) {
        try {
            UserData userData = new UserData("informer-fl-ru", "aFGgR5435");
            userData.setCompany(new ICompany() {

                @Override
                public String getCompanyName() {
                    return "flru";
                }

                @Override
                public Integer getCompanyId() {
                    return 1;
                }
            });
            try {
                LocalProcessResultHandler handler = new LocalProcessResultHandler();
                final SelectUnit nextPageSelectUnit = new SelectUnit("nextPageSelectUnit",
                        "<li class=\\\\\"b-pager__next\\\\\"><a href=\\\\\"(.*)\\\\\" id=\\\\\"PrevLink");
                new CrawlerBuilder(
                        chain(doGet("https://www.fl.ru/"),
                                waitUntilValidatoIsTrue(new StepConfigAdapter() {
                                    @Override
                                    public Collection<ISelector<?>> getParameters() {
                                        Collection<ISelector<?>> collection = new ArrayList<>();
                                        collection.add(new StaticStringSelector("login",
                                                "informer-fl-ru"));
                                        collection.add(new StaticStringSelector("passwd",
                                                "aFGgR5435"));
                                        collection.add(new StaticStringSelector("action", "login"));
                                        return collection;
                                    }

                                    @Override
                                    public HTTPMethod getMethod() {
                                        return HTTPMethod.POST;
                                    }

                                    @Override
                                    public String getUrl() {
                                        return "https://www.fl.ru/";
                                    }
                                }, new SelectorValidator(new XPathSelector(new SelectUnit(
                                        "loginPasswordInput", "//a[@class='b-bar__name']/@href")))),
                                doWhile(new ConditionalProcess(new SelectorValidator(
                                        new RegexpSelector(nextPageSelectUnit)), chain(
                                        assemble(new StepConfigAdapter() {
                                            @Override
                                            public Collection<ISelector<?>> getParameters() {
                                                Collection<ISelector<?>> collection = new ArrayList<>();
                                                collection.add(new HostStaticStringSelector(
                                                        "https://www.fl.ru/"));
                                                collection.add(new ActionRegexpSelector(
                                                        nextPageSelectUnit.getSelector()));
                                                return collection;
                                            }
                                        }),
                                        forEach(new XPathSelector(
                                                new SelectUnit("projectSelectorFromGroup",
                                                        "//div[@id='projects-list']/div[contains(@class, 'b-post')]")),
                                                chain(assemble(
                                                        new StepConfigAdapter() {

                                                            @Override
                                                            public Collection<ISelector<?>> getParameters() {
                                                                List<ISelector<?>> listOfSelectors = new ArrayList<>();
                                                                listOfSelectors
                                                                        .add(new ActionXPathSelector(
                                                                                "//a[@class='b-post__link']/@href"));
                                                                return listOfSelectors;
                                                            }

                                                            @Override
                                                            public String getUrl() {
                                                                return "https://www.fl.ru/";
                                                            }
                                                        },
                                                        new SessionPopulateHandler(
                                                                new XPathSelector(
                                                                        new SelectUnit("title",
                                                                                "//h2[contains(@class,'b-post__title b-post__title_inline')]/a/text()")))),
                                                        handlersOnly(new LocalProcessResultHandler()))))))),
                        userData).buildClient(createHttpClient(FIREFOX_USER_AGENT))
                        .buildCookieStore(getCookieStore())
                        .buildRequestBuilder(new BasicRequestBuilder()).getResult().collect();
            } catch (CrawlerException e) {
                throw e;
            }
            System.out.println("Logged in");
        } catch (CrawlerException e) {
            e.printStackTrace();
        }
    }

    private static class LocalProcessResultHandler implements IProcessResultHandler {
        final AtomicInteger counter = new AtomicInteger(1);
        long timer = System.currentTimeMillis();
        ISelector<String> selector = new XPathSelector(new SelectUnit("Headers",
                "//h2[contains(@class,'b-post__title b-post__title_inline')]/a/text()"));

        @Override
        public void handle(final IProcessSession session, final IEntity entity)
                throws CrawlerException {
            System.out.println((System.currentTimeMillis() - timer) + "ms. Number of records: ");
            int counterInt = 0;
            Collection<ISelectorResult> collection = selector.select(new String(
                    entity.getContent(), entity.getCharset()));
            for (ISelectorResult res : collection) {
                System.out.println(counter.getAndAdd(1) + "      >      " + res.getValue());
                ++counterInt;
            }
            System.out.println(counterInt);
            timer = System.currentTimeMillis();
        }
    }
}