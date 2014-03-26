package com.jdev.crawler.core.process;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.extract.ISelectorExtractStrategy;
import com.jdev.crawler.core.process.handler.MimeType;
import com.jdev.crawler.core.process.handler.MimeTypeUtil;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.process.model.TransferEntity;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.core.step.DummyValidator;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.core.step.IValidator;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.InvalidPageException;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.exception.UnsupportedMimeTypeException;

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
    private final List<IProcessResultHandler> handlers;

    private final IStepConfig config;

    /**
     * Accepted mime type.
     */
    protected MimeType[] acceptedTypes = new MimeType[] { MimeType.HTML };

    private final String description;

    private IValidator validator = new DummyValidator();

    private IRequestBuilder requestBuilder = null;

    protected AbstractStepProcess(final List<IProcessResultHandler> handlers,
            final IStepConfig config, final String description) {
        this.handlers = handlers;
        this.config = config;
        this.description = description;
    }

    public IValidator getValidator() {
        return validator;
    }

    public void setValidator(final IValidator validator) {
        this.validator = validator;
    }

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
     * @param context
     * @param extractStrategy
     * @return
     * @throws CrawlerException
     */
    protected abstract HttpRequestBase createRequest(final IProcessContext context,
            List<ISelectorResult> list) throws CrawlerException;

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

    protected IEntity executeRequest(final IProcessContext context, final HttpRequestBase request)
            throws CrawlerException, InterruptedException, IOException {
        int count = 0;
        boolean valid = false;
        IEntity entity;
        final boolean isEmpty = CollectionUtils.isEmpty(handlers);
        do {
            if (count > 0) {
                Thread.sleep(context.getWaitInterval());
            }
            entity = download(context, request);
            if (context.isStoreMarkup() && isEmpty) {
                com.jdev.crawler.core.process.FileUtils.storeMarkup(context, entity, this);
            }
        } while (++count < context.getRepeatTime() && !(valid = getValidator().validate(entity)));
        if (!valid) {
            throw new CrawlerException(format(
                    "Waiting {0} ms for a page {1} is failed. Operator is {2}",
                    context.getWaitInterval() * context.getRepeatTime(), request.getURI()
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

    /**
     * @param context
     * @param request
     * @return
     * @throws IOException
     */
    private IEntity download(final IProcessContext context, final HttpRequestBase request)
            throws IOException, InvalidPageException {
        final HttpClient client = context.getClient();
        final HttpResponse response = client.execute(request);
        final HttpEntity entity = response.getEntity();
        final TransferEntity resultEntity = new TransferEntity();
        String mimeType = entity.getContentType().getValue();
        resultEntity.setMimeType(mimeType);
        resultEntity.setCharset(Charset.forName(entity.getContentEncoding().getValue()));
        resultEntity.setStatusCode(response.getStatusLine().getStatusCode());
        if ((response.getStatusLine().getStatusCode() / 100) == 4) {
            throw new InvalidPageException("Page you are requested is not valid error code: "
                    + response.getStatusLine());
        }
        if (isMimeTypeAccepted(mimeType)) {
            try {
                final InputStream is = entity.getContent();
                try {
                    resultEntity.setContent(IOUtils.toByteArray(is));
                } finally {
                    is.close();
                }
            } finally {
                EntityUtils.consume(response.getEntity());
            }
        } else {
            throw new UnsupportedMimeTypeException(mimeType);
        }
        return resultEntity;
    }

    private boolean isMimeTypeAccepted(final String mimeType) {
        return MimeTypeUtil.findMime(mimeType, acceptedTypes) != null;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * @return the requestBuilder
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
