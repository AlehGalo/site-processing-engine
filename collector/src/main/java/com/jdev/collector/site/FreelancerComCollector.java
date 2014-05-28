package com.jdev.collector.site;

import static com.jdev.crawler.core.process.ProcessUtils.assemble;
import static com.jdev.crawler.core.process.ProcessUtils.chain;
import static com.jdev.crawler.core.process.ProcessUtils.doGet;
import static com.jdev.crawler.core.process.ProcessUtils.multi;

import java.util.ArrayList;
import java.util.Collection;

import com.jdev.collector.site.handler.FreelancerComHandler;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.ProcessUtils;
import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.simple.ActionStaticStringSelector;
import com.jdev.crawler.core.selector.simple.HostStaticStringSelector;
import com.jdev.crawler.core.selector.simple.MethodPostStaticStringSelector;
import com.jdev.crawler.core.selector.simple.StaticStringSelector;
import com.jdev.crawler.core.selector.xpath.ActionXPathSelector;
import com.jdev.crawler.core.step.StepConfigAdapter;
import com.jdev.crawler.core.step.validator.SelectorValidator;
import com.jdev.crawler.core.user.IUserData;

/**
 * @author Aleh
 * 
 */
public class FreelancerComCollector extends AbstractCollector {

    /**
     * @param userData
     */
    public FreelancerComCollector(final IUserData userData) {
        super(userData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.collector.site.AbstractCollector#initProcess()
     */
    @Override
    IProcess createProcess() {
        FreelancerComHandler handler = new FreelancerComHandler();
        IObserver observer = getEventHandlerDelegate();
        if (observer != null) {
            handler.addListener(observer);
        }
        return ProcessUtils
                .chain(doGet("https://www.freelancer.com"),
                        // assemble(new StepConfigAdapter() {
                        // @Override
                        // public Collection<ISelector<?>> getParameters() {
                        // Collection<ISelector<?>> selectorCollection = new
                        // ArrayList<>();
                        // selectorCollection.add(new
                        // MethodGetStaticStringSelector());
                        // selectorCollection.add(new HostStaticStringSelector(
                        // "https://cdn1.freelancer.com/fp/clear.png"));
                        // selectorCollection.add(new
                        // SelectiveValuesSelector<String>(
                        // new RegexpSelector(new SelectUnit("org_id",
                        // "org_id=(\\p{Alnum}{4,15})&amp;session_id")), 0));
                        // selectorCollection.add(new
                        // SelectiveValuesSelector<String>(
                        // new RegexpSelector(new SelectUnit("session_id",
                        // "session_id=(\\p{Alnum}{2,40})'")), 0));
                        // selectorCollection.add(new
                        // SelectiveValuesSelector<String>(
                        // new RegexpSelector(new SelectUnit("session2",
                        // "'\\) \\+ '(\\p{Alnum}{2,40})' \\+ unescape\\('")),
                        // 0));
                        // return selectorCollection;
                        //
                        // }
                        // }, new UriBasedRequestBuilder()),
                        assemble(new StepConfigAdapter() {
                            @Override
                            public Collection<ISelector<?>> getParameters() {
                                Collection<ISelector<?>> selectorCollection = new ArrayList<>();
                                selectorCollection.add(new ActionStaticStringSelector(
                                        "https://www.freelancer.com/ajax/login.php"));
                                selectorCollection.add(new MethodPostStaticStringSelector());
                                selectorCollection.add(new StaticStringSelector("username",
                                        getUserData().getLogin()));
                                selectorCollection.add(new StaticStringSelector("passwd",
                                        getUserData().getPassword()));
                                selectorCollection.add(new StaticStringSelector("savelogin", "on"));
                                return selectorCollection;
                            }
                        }),
                        doGet("https://www.freelancer.com/jobs/j-fixed-hourly/1"),
                        ProcessUtils
                                .doWhile(new ConditionalProcess(
                                        new SelectorValidator(
                                                new ActionXPathSelector(
                                                        "//span[@class='paginate_active']/following-sibling::span[1]/a/@href")),
                                        chain(multi(new StepConfigAdapter() {
                                            @Override
                                            public Collection<ISelector<?>> getParameters() {
                                                Collection<ISelector<?>> selectorCollection = new ArrayList<>();
                                                // TODO: add processing href="#"
                                                // urls
                                                selectorCollection
                                                        .add(new ActionXPathSelector(
                                                                "//table[@id='project_table_static']//tbody/tr[contains(@class,'project-details')]/td[1]/a[@href!='#']/@href"));
                                                return selectorCollection;
                                            }
                                        }, handler), assemble(new StepConfigAdapter() {
                                            @Override
                                            public Collection<ISelector<?>> getParameters() {
                                                Collection<ISelector<?>> collection = new ArrayList<>();
                                                collection.add(new HostStaticStringSelector(
                                                        "http://www.freelancer.com"));
                                                collection
                                                        .add(new ActionXPathSelector(
                                                                "//span[@class='paginate_active']/following-sibling::span[1]/a/@href"));
                                                return collection;
                                            }
                                        })))));
    }
}
