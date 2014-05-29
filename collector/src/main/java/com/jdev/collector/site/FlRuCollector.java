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
import com.jdev.crawler.core.selector.simple.HostStaticStringSelector;
import com.jdev.crawler.core.selector.simple.StaticStringSelector;
import com.jdev.crawler.core.selector.xpath.ActionXPathSelector;
import com.jdev.crawler.core.selector.xpath.XPathSelector;
import com.jdev.crawler.core.step.HTTPMethod;
import com.jdev.crawler.core.step.StepConfigAdapter;
import com.jdev.crawler.core.step.validator.SelectorValidator;
import com.jdev.crawler.core.user.IUserData;

/**
 * @author Aleh https://www.fl.ru/
 */
public class FlRuCollector extends AbstractCollector {

    /**
     * @param userData
     */
    public FlRuCollector(final IUserData userData) {
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
        IObserver observer = getEventHandlerDelegate();
        if (observer != null) {
            handler.addListener(observer);
        }
        final SelectUnit nextPageSelectUnit = new SelectUnit("nextPageSelectUnit",
                "//li[@class='b-pager__item b-pager__item_active']/following-sibling::li[1]/a/@href");
        final SelectUnit projectsLink = new SelectUnit("projectsLink",
                "//div[contains(@class, 'b-menu_tabs')]//ul[position()=2]/li[position()=2]/a/@href");
        return ProcessUtils.chain(ProcessUtils.doGet("https://www.fl.ru/"), ProcessUtils
                .waitUntilValidatoIsTrue(new StepConfigAdapter() {
                    @Override
                    public Collection<ISelector<?>> getParameters() {
                        Collection<ISelector<?>> collection = new ArrayList<>();
                        IUserData userData = getUserData();
                        collection.add(new StaticStringSelector("login", userData.getLogin()));
                        collection.add(new StaticStringSelector("passwd", userData.getPassword()));
                        collection.add(new StaticStringSelector("action", "login"));
                        collection.add(new StaticStringSelector("autologin", "1"));
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
                        "//img[@alt='" + getUserData().getLogin() + "']/@alt")))), ProcessUtils
                .assemble(new StepConfigAdapter() {

                    @Override
                    public Collection<ISelector<?>> getParameters() {
                        Collection<ISelector<?>> collection = new ArrayList<>();
                        collection.add(new HostStaticStringSelector("https://www.fl.ru/"));
                        collection.add(new ActionXPathSelector(projectsLink.getSelector()));
                        return collection;
                    }
                }), ProcessUtils.doWhile(new ConditionalProcess(new SelectorValidator(
                new XPathSelector(nextPageSelectUnit)), ProcessUtils.chain(
                ProcessUtils.assemble(new StepConfigAdapter() {
                    @Override
                    public Collection<ISelector<?>> getParameters() {
                        Collection<ISelector<?>> collection = new ArrayList<>();
                        collection.add(new HostStaticStringSelector("https://www.fl.ru/"));
                        collection.add(new ActionXPathSelector(nextPageSelectUnit.getSelector()));
                        return collection;
                    }
                }), ProcessUtils.multi(new StepConfigAdapter() {
                    @Override
                    public java.util.Collection<com.jdev.crawler.core.selector.ISelector<?>> getParameters() {
                        Collection<com.jdev.crawler.core.selector.ISelector<?>> parameters = new ArrayList<>();
                        parameters.add(new HostStaticStringSelector("https://www.fl.ru/"));
                        parameters.add(new ActionXPathSelector("//a[@class='b-post__link']/@href"));
                        return parameters;
                    }
                }, handler)))));
    }
}