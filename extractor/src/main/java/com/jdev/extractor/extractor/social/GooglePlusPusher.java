/**
 * 
 */
package com.jdev.extractor.extractor.social;

import java.net.URI;

import com.jdev.domain.social.Social;

/**
 * @author Aleh
 * 
 */
public class GooglePlusPusher extends FacebookPusher {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.extractor.extractor.social.AbstractSetOfStringsPusher#fillBean(java
     * .lang.Object, java.lang.String)
     */
    @Override
    public void fillBean(final Social e, final URI data) {
	e.setGooglePlusUri(data);
    }

}
