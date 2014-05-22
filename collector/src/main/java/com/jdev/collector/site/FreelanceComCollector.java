package com.jdev.collector.site;

import static com.jdev.crawler.core.process.ProcessUtils.chain;
import static com.jdev.crawler.core.process.ProcessUtils.doGet;
import static com.jdev.crawler.core.process.ProcessUtils.doWhile;
import static com.jdev.crawler.core.process.ProcessUtils.multi;

import java.util.ArrayList;
import java.util.Collection;

import com.jdev.collector.site.handler.FreelanceComHandler;
import com.jdev.collector.site.handler.IObserver;
import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.process.ProcessUtils;
import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.simple.HostStaticStringSelector;
import com.jdev.crawler.core.selector.xpath.ActionXPathSelector;
import com.jdev.crawler.core.step.StepConfigAdapter;
import com.jdev.crawler.core.step.validator.SelectorValidator;
import com.jdev.crawler.core.user.IUserData;

/**
 * @author Aleh http://www.freelance.com/
 */
public class FreelanceComCollector extends AbstractCollector {

    /**
     * @param userData
     */
    public FreelanceComCollector(final IUserData userData) {
        super(userData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.collector.site.AbstractCollector#initProcess()
     */
    @Override
    IProcess createProcess() {
        FreelanceComHandler fcHandler = new FreelanceComHandler();
        IObserver observer = getEventHandlerDelegate();
        if (observer != null) {
            fcHandler.addListener(getEventHandlerDelegate());
        }
        return chain(
                doGet("http://www.freelance.com/en/search/mission"),
                doWhile(new ConditionalProcess(new SelectorValidator(new ActionXPathSelector(
                        "//a[contains(text(), 'Next')]/@href")), ProcessUtils.chain(
                        multi(new StepConfigAdapter() {
                            @Override
                            public Collection<ISelector<?>> getParameters() {
                                Collection<com.jdev.crawler.core.selector.ISelector<?>> parameters = new ArrayList<>();
                                parameters.add(new HostStaticStringSelector(
                                        "http://www.freelance.com"));
                                parameters.add(new ActionXPathSelector(
                                        "//table[@id='result']/tbody//a/@href"));
                                return parameters;
                            }
                        }, fcHandler), ProcessUtils.assemble(new StepConfigAdapter() {
                            @Override
                            public Collection<ISelector<?>> getParameters() {
                                Collection<ISelector<?>> collection = new ArrayList<>();
                                collection.add(new HostStaticStringSelector(
                                        "http://www.freelance.com"));
                                collection.add(new ActionXPathSelector(
                                        "//a[contains(text(), 'Next')]/@href"));
                                return collection;
                            }
                        })))));
    }
}
