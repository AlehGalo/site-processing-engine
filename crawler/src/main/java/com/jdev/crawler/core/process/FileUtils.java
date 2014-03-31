package com.jdev.crawler.core.process;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.process.model.TransferEntity;
import com.jdev.crawler.core.user.IUserData;
import com.jdev.crawler.exception.InvalidPageException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public final class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
    }

    /**
     * @param context
     * @return
     */
    public static File getJobPath(final IProcessContext context) {
        return getJobPath(context.getUserData());
    }

    /**
     * @param userData
     * @return
     */
    public static File getJobPath(final IUserData userData) {
        final File companyRoot = new File(System.getProperty("java.io.tmpdir"), ""
                + userData.getCompany().getCompanyId());
        final File f = new File(companyRoot,
                isNotEmpty(userData.getUniqueKey()) ? userData.getUniqueKey() : userData.getLogin());
        final File file = new File(f, userData.getUUID());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(file.getAbsolutePath());
        }
        return file;
    }

    public static final void storeMarkup(final IProcessContext context, final IEntity entity,
            final IDescription desc) {
        try {
            final File parent = com.jdev.crawler.core.process.FileUtils.getJobPath(context);
            if (parent.exists() || parent.mkdirs()) {
                org.apache.commons.io.FileUtils.write(
                        new File(parent, getFileName(desc)),
                        new String(entity.getContent() == null ? "".getBytes() : entity
                                .getContent(), entity.getCharset()));
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

    /**
     * @param desc
     * @return
     */
    private static String getFileName(final IDescription desc) {
        return System.currentTimeMillis() + "_"
                + (desc.getDescription() == null ? StringUtils.EMPTY : desc.getDescription())
                + ".html";
    }

    /**
     * @param context
     * @param request
     * @return
     * @throws IOException
     */
    public static IEntity download(final HttpClient client, final HttpRequestBase request)
            throws IOException, InvalidPageException {
        Assert.notNull(client);
        Assert.notNull(request);
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
        // if (isMimeTypeAccepted(mimeType)) {
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
        // } else {
        // throw new UnsupportedMimeTypeException(mimeType);
        // }
        return resultEntity;
    }
}
