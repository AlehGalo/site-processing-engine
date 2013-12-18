/**
 * 
 */
package com.jdev.spider.core.extractor;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author Aleh
 * 
 */
public class DepthOfHref implements IDepth {

    /**
     * Limit for the depth.
     */
    private static final int DEPTH_ABSOLUTE_LIMIT = 128;

    /**
     * Standart path delimiter.
     */
    private static final String PATH_DELIMITER = "/";

    /**
     * Max level of url.
     */
    private final int depthMax;

    /**
     * @param depth
     *            max depth of url.
     */
    public DepthOfHref(final int depth) {
        Assert.isTrue(depth > 0 && depth < DEPTH_ABSOLUTE_LIMIT);
        depthMax = depth;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.spider.core.extractor.IDepth#isDepthAccepted(java.lang
     * .String)
     */
    @Override
    public boolean isDepthAccepted(final URL url) {
        if (url == null) {
            throw new IllegalArgumentException();
        }
        String path = url.getPath();
        return StringUtils.isEmpty(path)
                || StringUtils.removeStart(StringUtils.removeEnd(path, PATH_DELIMITER),
                        PATH_DELIMITER).split(PATH_DELIMITER).length <= depthMax;
    }
}
