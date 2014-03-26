package com.jdev.crawler.core.process.handler;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.apache.http.Consts;

/**
 * @author Aleh
 * 
 */
public final class MimeTypeUtil {

    /**
     * 
     */
    private MimeTypeUtil() {
    }

    /**
     * @param content
     *            byte stream.
     * @return true/false.
     */
    public static final boolean isHtmlContent(final byte content[]) {
        return !isEmpty(content) && new String(content, Consts.UTF_8).contains("<html");
    }

    /**
     * @param content
     *            file content.
     * @return true/false.
     */
    private static final boolean isEmpty(final byte content[]) {
        return content == null || content.length == 0;
    }

    /**
     * @param content
     *            byte stream.
     * @return true/false.
     */
    public static final boolean isPDFContent(final byte content[]) {
        return content != null
                && content.length > 5
                && (content[0] == 0x25 && content[1] == 0x50 && content[2] == 0x44
                        && content[3] == 0x46 && content[4] == 0x2D);
    }

    /**
     * @param content
     *            binary stream.
     * @return true/false.
     */
    public static final boolean isCSVContent(final byte content[]) {
        return !isPDFContent(content) && !isHtmlContent(content);
    }

    /**
     * @param mime
     *            string.
     * @return MimeType or null.
     */
    public static MimeType findMime(final String mime) {
        return findMime(mime, MimeType.values());
    }

    /**
     * @param mime
     *            string.
     * @return MimeType or null.
     */
    public static MimeType findMime(final String mimeString, final MimeType types[]) {
        if (isNotBlank(mimeString)) {
            for (MimeType mimeType : types) {
                if (contains(mimeString, mimeType.val)) {
                    return mimeType;
                }
            }
        }
        return null;
    }

}