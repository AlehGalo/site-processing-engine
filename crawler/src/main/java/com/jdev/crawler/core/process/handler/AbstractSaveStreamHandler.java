package com.jdev.crawler.core.process.handler;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.FileTypeEnum;
import com.jdev.crawler.core.process.FileUtils;
import com.jdev.crawler.core.process.IProcessContext;
import com.jdev.crawler.core.process.IProcessResultHandler;
import com.jdev.crawler.core.process.IProcessSession;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.store.IndexedItem;
import com.jdev.crawler.core.user.IStorageUniqueKey;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.InvalidPageException;

/**
 * Basic abstract save handler.
 */
public abstract class AbstractSaveStreamHandler implements IProcessResultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSaveStreamHandler.class);

    @Override
    public void handle(final IProcessSession session, final IEntity content)
            throws CrawlerException {
        final IProcessContext context = session.getSessionContext();
        final String uuid = session.getStringValue(RequestReservedWord.UUID.getWord()), dateName = session
                .getStringValue(RequestReservedWord.DATE.getWord()) == null ? "" : session
                .getStringValue(RequestReservedWord.DATE.getWord());
        context.getFileStorage()
                .getFileStore(new IStorageUniqueKey() {
                    @Override
                    public String getUniqueKey() {
                        return uuid;
                    }
                })
                .add(new IndexedItem(getFileType(), storeFile(context, content), dateName, session
                        .getStringValue(RequestReservedWord.DESCRIPTION.getWord())));
    }

    private String storeFile(final IProcessContext context, final IEntity content)
            throws CrawlerException {
        String result = null;
        if (validateFileContent(content.getContent())) {
            try {
                final ByteArrayInputStream bis = new ByteArrayInputStream(content.getContent());
                try {
                    final File parent = FileUtils.getJobPath(context);
                    if (parent.exists() || parent.mkdirs()) {
                        long time = System.currentTimeMillis();
                        final File target = new File(parent, "" + time + "."
                                + String.valueOf(getFileType()));
                        final BufferedOutputStream bos = new BufferedOutputStream(
                                new FileOutputStream(target));
                        try {
                            int inByte;
                            final byte buffer[] = new byte[1024];
                            while ((inByte = bis.read(buffer)) != -1) {
                                bos.write(buffer, 0, inByte);
                            }
                        } finally {
                            bos.close();
                        }
                        result = target.getAbsolutePath();
                    } else {
                        AbstractSaveStreamHandler.LOGGER.warn("Failed to create "
                                + parent.getAbsolutePath() + " folder.");
                    }
                } finally {
                    bis.close();
                }
            } catch (final Exception e) {
                throw new CrawlerException(e.getMessage(), e);
            }
        } else {
            throw new InvalidPageException(String.format("File is not a %s file.",
                    String.valueOf(getFileType())));
        }
        if (StringUtils.isEmpty(result)) {
            throw new CrawlerException("No files were saved for type " + getFileType().name());
        }
        return result;
    }

    /**
     * Dummy check file content.
     * 
     * @param content
     *            byte content.
     * @return true/false.
     */
    protected boolean validateFileContent(final byte[] content) {
        return true;
    }

    /**
     * PDF, CSV, HTML.
     * 
     * @return file extension.
     */
    protected abstract FileTypeEnum getFileType();
}