/**
 * 
 */
package com.sswf.desti.spider.core.extractor;

import java.net.URL;

/**
 * @author Aleh
 * 
 *         Check if URL can be accepted due to depth.
 */
public interface IDepth {

    /**
     * @param href
     *            to the resource.
     * @return true/false
     */
    boolean isDepthAccepted(URL href);

}
