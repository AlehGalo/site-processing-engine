/**
 * 
 */
package com.jdev.crawler.core.process;

import static java.lang.String.format;

import java.util.List;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.LoginException;

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
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy selectorExtractStrategy) throws CrawlerException {
        IEntity cont = null;
        int sizeBefore = 0;
        try {
            sizeBefore = extractSelectors(session.getSessionContext(), selectorExtractStrategy,
                    content).size();
            cont = super.process(session, content, selectorExtractStrategy);
        } catch (final CrawlerException e) {
            throw e;
        }
        int size = extractSelectors(session.getSessionContext(), selectorExtractStrategy, cont)
                .size();
        if (size != sizeBefore) {
            throw new LoginException(format("Logging in failed. For user=[%s].", session
                    .getSessionContext().getUserData().getLogin()));
        }
        return cont;
    }

}
