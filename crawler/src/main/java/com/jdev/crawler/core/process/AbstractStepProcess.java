package com.jdev.crawler.core.process;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.handler.MimeType;
import com.jdev.crawler.core.process.handler.MimeTypeUtil;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.selector.SelectUnit;
import com.jdev.crawler.core.selector.complex.ChainSelector;
import com.jdev.crawler.core.selector.entity.HeaderEntityContentEncodingSelector;
import com.jdev.crawler.core.selector.entity.HeaderEntityContentTypeSelector;
import com.jdev.crawler.core.settings.CrawlerSettings;
import com.jdev.crawler.core.settings.ISettings;
import com.jdev.crawler.core.step.DummyValidator;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.core.step.IValidator;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.SelectionException;

/**
 *
 */
public abstract class AbstractStepProcess implements IProcess, IDescription, IRequestBuilderContext {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStepProcess.class);
    // TODO: add logging

    /**
     * List of handler to be applied.
     */
    private final List<IProcessResultHandler> handlers;

    /**
     * 
     */
    private final IStepConfig config;

    /**
     * 
     */
    private final ISettings settings = new CrawlerSettings();

    /**
     * Accepted mime type.
     */
    protected MimeType[] acceptedTypes = new MimeType[] { MimeType.HTML };

    /**
     * 
     */
    private final String description;

    /**
     * 
     */
    private IValidator validator = new DummyValidator();

    /**
     * 
     */
    private IRequestBuilder requestBuilder = null;

    /**
     * 
     */
    private final ISelector<HttpEntity> entitySelector;

    /**
     * @param handlers
     * @param config
     * @param description
     */
    protected AbstractStepProcess(final List<IProcessResultHandler> handlers,
            final IStepConfig config, final String description) {
        this.handlers = handlers;
        this.config = config;
        this.description = description;
        SelectUnit selectUnit = new SelectUnit("charset", "charset");
        entitySelector = new ChainSelector<HttpEntity>(new HeaderEntityContentEncodingSelector(
                selectUnit), new HeaderEntityContentTypeSelector(selectUnit));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.IProcess#process(com.jdev.crawler.core.
     * process.IProcessSession, com.jdev.crawler.core.process.model.IEntity,
     * com.jdev.crawler.core.process.extract.ISelectorExtractStrategy)
     */
    @Override
    public IEntity process(final IProcessSession session, final IEntity content,
            final ISelectorExtractStrategy selectorExtractStrategy) throws CrawlerException {
        try {
            IProcessContext context = session.getSessionContext();
            List<ISelectorResult> selectors = extractSelectors(context, selectorExtractStrategy,
                    content);
            HttpRequestBase request = createRequest(context, selectors);
            IEntity result = executeRequest(context, request);
            return handle(session, result);
        } catch (final IOException | InterruptedException ex) {
            throw new CrawlerException(ex.getMessage(), ex);
        }
    }

    /**
     * @param session
     * @param content
     * @return
     * @throws CrawlerException
     */
    protected IEntity handle(final IProcessSession session, final IEntity entity)
            throws CrawlerException {
        if (CollectionUtils.isNotEmpty(handlers)) {
            for (final IProcessResultHandler handler : handlers) {
                handler.handle(session, entity);
            }
        }
        return entity;
    }

    /**
     * @param context
     * @param request
     * @return
     * @throws CrawlerException
     * @throws InterruptedException
     * @throws IOException
     */
    protected IEntity executeRequest(final IProcessContext context, final HttpRequestBase request)
            throws CrawlerException, InterruptedException, IOException {
        int count = 0;
        boolean valid = false;
        IEntity entity;
        final boolean isEmpty = CollectionUtils.isEmpty(handlers);
        do {
            if (count > 0) {
                Thread.sleep(settings.getWaitInterval());
            }
            entity = com.jdev.crawler.core.process.HttpClientUtils.download(
                    context.getHttpClient(), request, entitySelector);

            // TODO: add support.
            // if ((response.getStatusLine().getStatusCode() / 100) == 4) {
            // throw new
            // InvalidPageException("Page you are requested is not valid error code: "
            // + response.getStatusLine());
            // }

            // TODO: add support for mime type
            if (settings.isStoremarkup() && isEmpty) {
                com.jdev.crawler.core.process.HttpClientUtils.storeMarkup(context, entity, this);
            }
        } while (++count < settings.getRepeatTime() && !(valid = getValidator().validate(entity)));
        if (!valid) {
            throw new CrawlerException(format(
                    "Waiting {0} ms for a page {1} is failed. Operator is {2}",
                    settings.getWaitInterval() * settings.getRepeatTime(), request.getURI()
                            .toASCIIString(), context.getUserData().getCompany().getCompanyName()));
        } else {
            return entity;
        }
    }

    /**
     * @return list of selector results.
     * @throws SelectionException
     */
    protected List<ISelectorResult> extractSelectors(final IProcessContext context,
            final ISelectorExtractStrategy extractStrategy, final IEntity content)
            throws SelectionException {
        return extractStrategy.extractSelectors(context, config, content);
    }

    public IValidator getValidator() {
        return validator;
    }

    public void setValidator(final IValidator validator) {
        this.validator = validator;
    }

    /**
     * @param context
     * @param extractStrategy
     * @return
     * @throws CrawlerException
     */
    protected abstract HttpRequestBase createRequest(final IProcessContext context,
            List<ISelectorResult> list) throws CrawlerException;

    /**
     * @param mimeType
     * @return
     */
    private boolean isMimeTypeAccepted(final String mimeType) {
        return MimeTypeUtil.findMime(mimeType, acceptedTypes) != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IDescription#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return the requestBuilder
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.IRequestBuilderContext#getRequestBuilder()
     */
    @Override
    public final IRequestBuilder getRequestBuilder() {
        return requestBuilder;
    }

    /**
     * @param requestBuilder
     *            the requestBuilder to set
     */
    public final void setRequestBuilder(final IRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    /**
     * @param acceptedTypes
     *            the acceptedTypes to set
     */
    public final void setAcceptedTypes(final MimeType[] acceptedTypes) {
        this.acceptedTypes = acceptedTypes;
    }
}
