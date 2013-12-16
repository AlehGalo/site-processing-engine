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
public class PineterestPusher extends FacebookPusher {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sswf.desti.extractor.social.FacebookPusher#fillBean(com.sswf.desti
     * .domain.social.Social, java.net.URI)
     */
    @Override
    public void fillBean(final Social s, final URI data) {
	s.setPineterestUri(data);
    }

}
