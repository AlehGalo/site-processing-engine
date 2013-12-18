/**
 * 
 */
package com.jdev.extractor.extractor.social;

import java.net.URI;
import java.util.Set;

import com.jdev.domain.social.Social;

/**
 * @author Aleh
 * 
 */
public class FacebookPusher extends
	AbstractSetOfStringsPusher<Set<String>, Social> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.extractor.extractor.social.AbstractSetOfStringsPusher#fillBean(java
     * .lang.Object, java.lang.String)
     */
    @Override
    public void fillBean(final Social s, final URI data) {
	s.setFacebookUri(data);
    }

}
