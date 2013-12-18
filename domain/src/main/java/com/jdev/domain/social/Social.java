package com.jdev.domain.social;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Aleh
 */
public class Social implements ISocial {

    private final Map<SocialNetwork, SocialNetworkLink> links = new EnumMap<>(SocialNetwork.class);

    @Override
    public URI getFacebookUri() {
        return linkFor(SocialNetwork.FACEBOOK);
    }

    private URI linkFor(SocialNetwork network) {
        SocialNetworkLink link = links.get(network);
        if (link == null) {
            return null;
        }
        return link.getLink();
    }

    public void setFacebookUri(final URI facebookUri) {
        putLink(SocialNetwork.FACEBOOK, facebookUri);
    }

    private void putLink(SocialNetwork network, URI link) {
        if (link != null) {
            links.put(network, new SocialNetworkLink(network, link));
        } else {
            links.remove(network);
        }
    }

    @Override
    public URI getTwitterUri() {
        return linkFor(SocialNetwork.TWITTER);
    }

    public void setTwitterUri(final URI twitterUri) {
        putLink(SocialNetwork.TWITTER, twitterUri);
    }

    @Override
    public URI getInstagramUri() {
        return linkFor(SocialNetwork.INSTAGRAM);
    }

    public void setInstagramUri(final URI instagramUri) {
        putLink(SocialNetwork.INSTAGRAM, instagramUri);
    }

    @Override
    public URI getPineterestUri() {
        return linkFor(SocialNetwork.PINTEREST);
    }

    public void setPineterestUri(final URI pineterestUri) {
        putLink(SocialNetwork.PINTEREST, pineterestUri);
    }

    @Override
    public URI getGooglePlusUri() {
        return linkFor(SocialNetwork.GOOGLE_PLUS);
    }

    public void setGooglePlusUri(final URI googlePlusUri) {
        putLink(SocialNetwork.GOOGLE_PLUS, googlePlusUri);
    }

    @Override
    public Collection<SocialNetworkLink> getAllLinks() {
        return links.values();
    }

    @Override
    public String allLinksAsCsv() {
        return joinLinksWith(",");
    }

    @Override
    public String toString() {
        return "Social :\n" + joinLinksWith("\n");
    }

    private String joinLinksWith(String separator) {
        List<String> res = new ArrayList<>();
        for (SocialNetworkLink link : links.values()) {
            res.add(link.getNetwork().getName() + " " + link.getLink().toString());
        }

        return StringUtils.join(res, separator);
    }

}