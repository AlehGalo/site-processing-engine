/**
 * 
 */
package com.jdev.crawler.core.process.handler;

import com.jdev.crawler.core.FileTypeEnum;


/**
 * @author Aleh
 * 
 */
public class SavePDFStreamHandler extends AbstractSaveStreamHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.handler.AbstractSaveStreamHandler#
     * checkFileContent(byte[])
     */
    @Override
    protected boolean validateFileContent(byte[] content) {
        return MimeTypeUtil.isPDFContent(content);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.handler.AbstractSaveStreamHandler#
     * getFileExtension()
     */
    @Override
    protected FileTypeEnum getFileType() {
        return FileTypeEnum.PDF;
    }

}
