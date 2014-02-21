/**
 * 
 */
package com.jdev.crawler.core.process.handler;

import java.nio.charset.Charset;
import java.util.Collection;

import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class SessionPopulateHandler implements IProcessResultHandler {

    /**
     * Request reserved word.
     */
    private final ISelector<String> selector;

    /**
     * @param selector
     * 
     */
    public SessionPopulateHandler(final ISelector<String> selector) {
        Assert.notNull(selector);
        this.selector = selector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.populate.ISessionPropertyPopulate#
     * populateProperty(com.jdev.crawler.core.process.IProcessSession,
     * cinergy.crawler.core.selector.ISelectorResult)
     */
    @Override
    public void handle(final IProcessSession session, final byte[] content)
            throws SelectionException {
        Assert.notNull(session);
        Assert.notNull(content);
        final Collection<ISelectorResult> result = selector.select(new String(content,
                Charset.forName("UTF-8")));
        Assert.isTrue(result.size() == 1);
        final ISelectorResult selRes = (ISelectorResult) result.toArray()[0];
        session.putValue(selRes.getName(), selRes.getValue());
    }

}
