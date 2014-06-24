/**
 * 
 */
package com.jdev.crawler.core.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Collection;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.process.model.TransferEntity;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.InvalidPageException;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
final class HttpClientUtils {

    /**
     * 
     */
    private HttpClientUtils() {
    }

    /**
     * @param client
     * @param request
     * @param mimeSelectorResult
     * @return
     * @throws IOException
     * @throws InvalidPageException
     */
    public static IEntity download(final HttpClient client, final HttpRequestBase request,
            final ISelector<HttpEntity> entitySelector) throws IOException, InvalidPageException {
        Assert.notNull(client);
        Assert.notNull(request);
        final HttpResponse response = client.execute(request);
        final HttpEntity entity = response.getEntity();
        final TransferEntity resultEntity = new TransferEntity();

        String mimeType = entity.getContentType().getValue();
        resultEntity.setMimeType(mimeType);
        Charset charset = null;
        try {
            Collection<ISelectorResult> collection = entitySelector.select(entity);
            if (!collection.isEmpty()) {
                for (ISelectorResult iSelectorResult : collection) {
                    String valueResult = iSelectorResult.getValue();
                    try {
                        charset = Charset.forName(valueResult);
                    } catch (UnsupportedCharsetException e) {
                        // TODO: add logger.
                    }
                }
            }
        } catch (SelectionException e) {
            throw new InvalidPageException(e.getMessage());
        }

        // TODO: add logging
        resultEntity.setCharset(charset == null ? Charsets.UTF_8 : charset);
        resultEntity.setStatusCode(response.getStatusLine().getStatusCode());
        resultEntity.setEntityUri(request.getURI());
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
        return resultEntity;
    }

    /**
     * @param context
     * @param entity
     * @param desc
     */
    public static final void storeMarkup(final IProcessContext context, final IEntity entity,
            final IDescription desc) {
        try {
            final File parent = com.jdev.crawler.core.process.FileUtils.getJobPath(context);
            if (parent.exists() || parent.mkdirs()) {
                org.apache.commons.io.FileUtils.write(
                        new File(parent, FileUtils.getFileName(desc)),
                        new String(entity.getContent() == null ? "".getBytes() : entity
                                .getContent(), entity.getCharset()), entity.getCharset());
            } else {
                // TODO: implement logging
                // AbstractStepProcess.LOGGER.warn("Failed to create " +
                // parent.getAbsolutePath()
                // + " folder.");
            }
        } catch (final IOException e) {
            // TODO: implement logging
            // AbstractStepProcess.LOGGER.warn(e.getMessage(), e);
        }
    }

}
