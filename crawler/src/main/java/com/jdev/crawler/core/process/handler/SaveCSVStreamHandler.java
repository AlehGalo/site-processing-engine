/**
 * 
 */
package com.jdev.crawler.core.process.handler;

import com.jdev.crawler.core.FileType;

/**
 * @author Aleh
 * 
 */
public class SaveCSVStreamHandler extends AbstractSaveStreamHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.handler.AbstractSaveStreamHandler#
     * getFileExtension()
     */
    @Override
    protected FileType getFileType() {
        return FileType.CSV;
    }

    /* (non-Javadoc)
     * @see com.jdev.crawler.core.process.handler.AbstractSaveStreamHandler#checkFileContent(byte[])
     */
    @Override
    protected boolean checkFileContent(byte[] content) {
        return MimeTypeUtil.isCSVContent(content);
    }
}
