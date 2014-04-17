/**
 * 
 */
package com.jdev.collector.site;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Element;

import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.ProcessUtils;
import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.jsoup.StringSourceJSoupSelector;
import com.jdev.crawler.core.selector.jsoup.extractor.IJSoupElementExtractor;
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
 * @author Aleh https://www.fl.ru/
 */
public class FlRuCollector extends AbstractCollector {

    // Mail account
    // informer.email@yandex.ru
    // AdfdGG#r%$#@$55345

    // informer-fl-ru
    // aFGgR5435

    /**
     * @param userData
     */
    public FlRuCollector(UserData userData, IProcess process) {
        super(userData, process);
        userDataLocal = new UserData("informer-fl-ru", "aFGgR5435");
        userDataLocal.setCompany(new ICompany() {

            @Override
            public String getCompanyName() {
                return "flru";
            }

            @Override
            public Integer getCompanyId() {
                return 1;
            }
        });

        final LocalProcessResultHandler handler = new LocalProcessResultHandler();
        final SelectUnit nextPageSelectUnit = new SelectUnit("nextPageSelectUnit",
                "<li class=\\\\\"b-pager__next\\\\\"><a href=\\\\\"(.*)\\\\\" id=\\\\\"PrevLink");
        process = ProcessUtils.chain(ProcessUtils.doGet("https://www.fl.ru/"), ProcessUtils
                .waitUntilValidatoIsTrue(new StepConfigAdapter() {
                    @Override
                    public Collection<ISelector<?>> getParameters() {
                        Collection<ISelector<?>> collection = new ArrayList<>();
                        collection.add(new StaticStringSelector("login", "informer-fl-ru"));
                        collection.add(new StaticStringSelector("passwd", "aFGgR5435"));
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
                }, new SelectorValidator(new XPathSelector(new SelectUnit("loginPasswordInput",
                        "//a[@class='b-bar__name']/@href")))), ProcessUtils
                .doWhile(new ConditionalProcess(new SelectorValidator(new RegexpSelector(
                        nextPageSelectUnit)), ProcessUtils.chain(
                        ProcessUtils.assemble(new StepConfigAdapter() {
                            @Override
                            public Collection<ISelector<?>> getParameters() {
                                Collection<ISelector<?>> collection = new ArrayList<>();
                                collection.add(new HostStaticStringSelector("https://www.fl.ru/"));
                                collection.add(new ActionRegexpSelector(nextPageSelectUnit
                                        .getSelector()));
                                return collection;
                            }
                        }), ProcessUtils.multi(new StepConfigAdapter() {
                            @Override
                            public java.util.Collection<com.jdev.crawler.core.selector.ISelector<?>> getParameters() {
                                Collection<com.jdev.crawler.core.selector.ISelector<?>> parameters = new ArrayList<>();
                                parameters.add(new HostStaticStringSelector("https://www.fl.ru/"));
                                parameters.add(new ActionXPathSelector(
                                        "//a[@class='b-post__link']/@href"));
                                return parameters;
                            }
                        }, handler)))));
    }

    private static class LocalProcessResultHandler implements IProcessResultHandler {
        final AtomicInteger counter = new AtomicInteger(1);
        long timer = System.currentTimeMillis();

        /**
         * 
         */
        private final ISelector<String> titleSelector = new XPathSelector(new SelectUnit("Headers",
                "//h1[contains(@class,'b-page__title b-page__title_ellipsis')]/text()"));

        /**
         * 
         */
        private final StringSourceJSoupSelector contentSelector = new StringSourceJSoupSelector(
                new SelectUnit("Content", "div.prj_text"));

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

    @Override
    public void congregate() {
        try {
            crawler.collect();
        } catch (CrawlerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}