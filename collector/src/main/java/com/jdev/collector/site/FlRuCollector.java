/**
 * 
 */
package com.jdev.collector.site;

import java.util.ArrayList;
import java.util.Collection;

import com.jdev.collector.site.handler.FlRuHandler;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.ProcessUtils;
import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.selector.ISelector;
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
import com.jdev.crawler.core.user.UserData;

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
     * 
     */
    private IObserver objserver;

    /**
     * @param userData
     */
    public FlRuCollector(final UserData userData) {
        super(userData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.collector.site.AbstractCollector#initProcess()
     */
    @Override
    IProcess createProcess() {
        FlRuHandler handler = new FlRuHandler();
        handler.addListener(getObjserver());
        final SelectUnit nextPageSelectUnit = new SelectUnit("nextPageSelectUnit",
                "<li class=\\\\\"b-pager__next\\\\\"><a href=\\\\\"(.*)\\\\\" id=\\\\\"PrevLink");
        return ProcessUtils.chain(ProcessUtils.doGet("https://www.fl.ru/"), ProcessUtils
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

    /**
     * @return the objserver
     */
    public IObserver getObjserver() {
        return objserver;
    }

    /**
     * @param objserver
     *            the objserver to set
     */
    public void setObjserver(final IObserver objserver) {
        this.objserver = objserver;
    }

}