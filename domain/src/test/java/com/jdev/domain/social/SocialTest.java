package com.jdev.domain.social;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 */
public class SocialTest {

    @Test
    public void facebookUri() throws URISyntaxException {
        Social social = new Social();
        URI uri = new URI("https://facebook.com/somegroup/");
        social.setFacebookUri(uri);
        assertEquals(uri, social.getFacebookUri());
    }

    @Test
    public void twitterUri() throws URISyntaxException {
        Social social = new Social();
        URI uri = new URI("https://twitter.com/somename/");
        social.setTwitterUri(uri);
        assertEquals(uri, social.getTwitterUri());
    }

    @Test
    public void googlePlusUri() throws URISyntaxException {
        Social social = new Social();
        URI uri = new URI("https://plus.google.com/somelin/");
        social.setGooglePlusUri(uri);
        assertEquals(uri, social.getGooglePlusUri());
    }

    @Test
    public void pinterestUri() throws URISyntaxException {
        Social social = new Social();
        URI uri = new URI("https://pinterest.com/somegroup/");
        social.setPineterestUri(uri);
        assertEquals(uri, social.getPineterestUri());
    }

    @Test
    public void instagramUri() throws URISyntaxException {
        Social social = new Social();
        URI uri = new URI("https://instagram.com/lin/");
        social.setInstagramUri(uri);
        assertEquals(uri, social.getInstagramUri());
    }

    @Test
    public void getAllLinks() throws URISyntaxException {
        Social social = new Social();

        URI instagramUri = new URI("https://instagram.com/lin/");
        URI pineterestUri = new URI("https://pinterest.com/somegroup/");
        URI facebookUri = new URI("https://facebook.com/somegroup/");

        social.setInstagramUri(instagramUri);
        social.setPineterestUri(pineterestUri);
        social.setFacebookUri(facebookUri);

        Set<SocialNetworkLink> allLinks = new HashSet<>(social.getAllLinks());

        Set<SocialNetworkLink> expected = new HashSet<>();
        expected.add(new SocialNetworkLink(SocialNetwork.INSTAGRAM, instagramUri));
        expected.add(new SocialNetworkLink(SocialNetwork.PINTEREST, pineterestUri));
        expected.add(new SocialNetworkLink(SocialNetwork.FACEBOOK, facebookUri));

        assertEquals(expected, allLinks);

    }

}
