/**
 * 
 */
package com.jdev.crawler.core.process.handler;

import com.jdev.crawler.core.FileTypeEnum;

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
    protected FileTypeEnum getFileType() {
        return FileTypeEnum.CSV;
    }

    /* (non-Javadoc)
     * @see com.jdev.crawler.core.process.handler.AbstractSaveStreamHandler#checkFileContent(byte[])
     */
    @Override
    protected boolean validateFileContent(byte[] content) {
        return MimeTypeUtil.isCSVContent(content);
    }
}
