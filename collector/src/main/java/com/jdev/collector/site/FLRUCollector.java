/**
 * 
 */
package com.jdev.collector.site;

import static com.jdev.crawler.core.AgentEnum.FIREFOX_USER_AGENT;
import static com.jdev.crawler.core.HttpClientFactory.createHttpClient;
import static com.jdev.crawler.core.HttpClientFactory.getCookieStore;
import static com.jdev.crawler.core.process.ProcessUtils.chain;
import static com.jdev.crawler.core.process.ProcessUtils.doGet;
import static com.jdev.crawler.core.process.ProcessUtils.login;

import java.util.ArrayList;
import java.util.Collection;

import com.jdev.crawler.builder.CrawlerBuilder;
import com.jdev.crawler.core.request.BasicRequestBuilder;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.simple.StaticTextSelector;
import com.jdev.crawler.core.step.StepConfigAdapter;
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
            new CrawlerBuilder(chain(doGet("https://www.fl.ru/"), login(new StepConfigAdapter() {
                @Override
                public Collection<ISelector<?>> getParameters() {
                    Collection<ISelector<?>> collection = new ArrayList<>();
                    collection.add(new StaticTextSelector("login", "informer-fl-ru"));
                    collection.add(new StaticTextSelector("passwd", "aFGgR5435"));
                    collection.add(new StaticTextSelector("action", "login"));
                    return collection;
                }

                @Override
                public String getMethod() {
                    return "POST";
                }

                @Override
                public String getUrl() {
                    return "https://www.fl.ru/";
                }
            })), userData).buildClient(createHttpClient(FIREFOX_USER_AGENT))
                    .buildCookieStore(getCookieStore())
                    .buildRequestBuilder(new BasicRequestBuilder()).getResult().collect();
        } catch (CrawlerException e) {
            e.printStackTrace();
        }

    }
}
