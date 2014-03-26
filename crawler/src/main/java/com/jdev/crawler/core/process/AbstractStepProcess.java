package com.jdev.crawler.core.process;

import static java.text.MessageFormat.format;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
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
    public byte[] process(final IProcessSession session, final byte[] content,
            final ISelectorExtractStrategy selectorExtractStrategy) throws CrawlerException {
        try {
            IProcessContext context = session.getSessionContext();
            List<ISelectorResult> selectors = extractSelectors(context, selectorExtractStrategy,
                    content);
            HttpRequestBase request = createRequest(context, selectors);
            byte[] result = executeRequest(context, request);
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
    protected byte[] handle(final IProcessSession session, final byte[] content)
            throws CrawlerException {
        if (CollectionUtils.isNotEmpty(handlers)) {
            for (final IProcessResultHandler handler : handlers) {
                handler.handle(session, content);
            }
        }
        return content;
    }

    protected byte[] executeRequest(final IProcessContext context, final HttpRequestBase request)
            throws CrawlerException, InterruptedException, IOException {
        int count = 0;
        boolean valid = false;
        byte[] b;
        final boolean isEmpty = CollectionUtils.isEmpty(handlers);
        do {
            if (count > 0) {
                Thread.sleep(context.getWaitInterval());
            }
            b = download(context, request);
            if (context.isStoreMarkup() && isEmpty) {
                storeMarkup(context, b);
            }
        } while (++count < context.getRepeatTime() && !(valid = getValidator().validate(b)));
        if (!valid) {
            throw new CrawlerException(format(
                    "Waiting {0} ms for a page {1} is failed. Operator is {2}",
                    context.getWaitInterval() * context.getRepeatTime(), request.getURI()
                            .toASCIIString(), context.getUserData().getCompany().getCompanyName()));
        } else {
            return b;
        }
    }

    /**
     * @return list of selector results.
     * @throws SelectionException
     */
    protected List<ISelectorResult> extractSelectors(final IProcessContext context,
            final ISelectorExtractStrategy extractStrategy, final byte[] content)
            throws SelectionException {
        return extractStrategy.extractSelectors(context, config, content);
    }

    /**
     * @param context
     * @param request
     * @return
     * @throws IOException
     */
    private byte[] download(final IProcessContext context, final HttpRequestBase request)
            throws IOException, InvalidPageException {
        byte[] content = null;
        final HttpClient client = context.getClient();
        final HttpResponse response = client.execute(request);
        final HttpEntity entity = response.getEntity();
        String mimeType = entity.getContentType().getValue();
        if ((response.getStatusLine().getStatusCode() / 100) == 4) {
            throw new InvalidPageException("Page you are requested is not valid error code: "
                    + response.getStatusLine());
        }
        if (isMimeTypeAccepted(mimeType)) {
            try {
                final InputStream is = entity.getContent();
                try {
                    content = IOUtils.toByteArray(is);
                } finally {
                    is.close();
                }
            } finally {
                EntityUtils.consume(response.getEntity());
            }
        } else {
            throw new UnsupportedMimeTypeException(mimeType);
        }
        return content;
    }

    private void storeMarkup(final IProcessContext context, final byte[] content) {
        try {
            final File parent = com.jdev.crawler.core.process.FileUtils.getJobPath(context);
            if (parent.exists() || parent.mkdirs()) {
                FileUtils.write(new File(parent, getFileName()),
                        new String(content == null ? "".getBytes() : content, Consts.UTF_8));
            } else {
                AbstractStepProcess.LOGGER.warn("Failed to create " + parent.getAbsolutePath()
                        + " folder.");
            }
        } catch (final IOException e) {
            AbstractStepProcess.LOGGER.warn(e.getMessage(), e);
        }
    }

    private String getFileName() {
        return System.currentTimeMillis() + "_" + this.getClass().getCanonicalName() + ".html";
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
