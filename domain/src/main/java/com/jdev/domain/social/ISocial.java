package com.jdev.domain.social;

import java.net.URI;
import java.util.Collection;

/**
 * @author Aleh
 * 
 */
public interface ISocial {

    /**
     * @return the twitterUri
     */
    URI getTwitterUri();

    /**
     * @return the instagramUri
     */
    URI getInstagramUri();

    /**
     * @return the pineterestUri
     */
    URI getPineterestUri();

    /**
     * @return the googlePlusUri
     */
    URI getGooglePlusUri();

    /**
     * @return twitter url.
     */
    URI getFacebookUri();

    /**
     * @return all saved links
     */
    Collection<SocialNetworkLink> getAllLinks();

    String allLinksAsCsv();

}
