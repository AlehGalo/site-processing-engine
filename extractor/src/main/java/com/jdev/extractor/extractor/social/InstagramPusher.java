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
public class InstagramPusher extends FacebookPusher {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.extractor.extractor.social.FacebookPusher#fillBean(com.jdev.extractor
     * .domain.social.Social, java.net.URI)
     */
    @Override
    public void fillBean(final Social s, final URI data) {
	s.setInstagramUri(data);
    }
}
