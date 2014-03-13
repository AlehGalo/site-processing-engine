/**
 * 
 */
package com.jdev.crawler.core.selector.cookie;

import java.io.IOException;

import org.apache.http.client.CookieStore;

import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.TestAbstractResourcableSelector;

/**
 * @author Aleh
 * 
 */
public class TestCookiesSelector extends TestAbstractResourcableSelector<CookieStore> {

    /**
     * @param resourceFileName
     *            file name of resource.
     * @param selectorFileName
     *            selector for resource.
     * @param resultFileName
     *            result of selector.
     * @throws IOException
     *             exception.
     */
    public TestCookiesSelector(final String resourceFileName, final String selectorFileName,
            final String resultFileName) throws IOException {
        super(resourceFileName, selectorFileName, resultFileName);
    }

    // @Override
    @Override
    public ISelector<CookieStore> createSelector() {
        return new CookieSelector(getSelectorString());
    }

    // @Override
    @Override
    public CookieStore convertStringToParameter(final String par) {
        // BasicClientCookie cookies = new BasicClientCookie();
        // cookies.setDomain(domain);
        // return new BasicCookieStore().;
        return null;
    }
}
