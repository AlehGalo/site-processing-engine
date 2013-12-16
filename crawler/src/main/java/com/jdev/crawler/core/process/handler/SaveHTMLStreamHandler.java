/**
 * 
 */
package com.jdev.crawler.core.process.handler;

import com.jdev.crawler.core.FileType;

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
    protected FileType getFileType() {
        return FileType.HTML;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.handler.AbstractSaveStreamHandler
     * #checkFileContent(byte[])
     */
    @Override
    protected boolean checkFileContent(final byte[] content) {
        return MimeTypeUtil.isHtmlContent(content);
    }

}
