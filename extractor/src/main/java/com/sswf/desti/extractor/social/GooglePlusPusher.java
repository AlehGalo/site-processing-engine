/**
 * 
 */
package com.sswf.desti.extractor.social;

import java.net.URI;

import com.sswf.desti.domain.social.Social;

/**
 * @author Aleh
 * 
 */
public class GooglePlusPusher extends FacebookPusher {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sswf.desti.extractor.social.AbstractSetOfStringsPusher#fillBean(java
     * .lang.Object, java.lang.String)
     */
    @Override
    public void fillBean(final Social e, final URI data) {
	e.setGooglePlusUri(data);
    }

}
