/**
 * 
 */
package com.jdev.crawler.core.process;

import java.util.List;

import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.LoginException;
import com.jdev.crawler.exception.SelectionException;

/**
 * @author Aleh
 * 
 */
public class LoginStepProcess extends AssembledStepProcess {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param config
     * @param handlers
     */
    public LoginStepProcess(final IStepConfig config, final List<IProcessResultHandler> handlers) {
        super(config, handlers);
    }

    /**
     * @param config
     * @param handlers
     * @param requestBuilder
     */
    public LoginStepProcess(final IStepConfig config, final List<IProcessResultHandler> handlers,
            final IRequestBuilder requestBuilder) {
        super(config, handlers, requestBuilder);
    }

    @Override
    public byte[] process(final IProcessSession session, final byte[] content,
            final ISelectorExtractStrategy selectorExtractStrategy) throws CrawlerException {
        byte[] cont = null;
        try {
            cont = super.process(session, content, selectorExtractStrategy);
        } catch (final CrawlerException e) {
            throw e;
        }
        try {
            super.createRequest(session.getSessionContext(),
                    extractSelectors(session.getSessionContext(), selectorExtractStrategy, cont));
        } catch (final SelectionException se) {
            // All if fine. Input elements are absent as a result login screen
            // is ok.
            return cont;
        }
        throw new LoginException(String.format("Logging in failed. For user=[%s].", session
                .getSessionContext().getUserData().getLogin()));
    }

}
