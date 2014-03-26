/**
 * 
 */
package com.jdev.crawler.core.process.handler;

import com.jdev.crawler.core.FileTypeEnum;

/**
 * @author Aleh
 * 
 */
public class SaveHTMLStreamHandler extends AbstractSaveStreamHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.handler.AbstractSaveStreamHandler#
     * getFileExtension()
     */
    @Override
    protected FileTypeEnum getFileType() {
        return FileTypeEnum.HTML;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.handler.AbstractSaveStreamHandler
     * #checkFileContent(byte[])
     */
    @Override
    protected boolean validateFileContent(final byte[] content) {
        return MimeTypeUtil.isHtmlContent(content);
    }

}
