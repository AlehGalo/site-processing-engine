package com.jdev.domain.social;

import java.net.URI;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Al
 */
public class SocialNetworkLink {

    private final SocialNetwork network;
    private final URI link;

    public SocialNetworkLink(SocialNetwork network, URI link) {
        this.network = network;
        this.link = link;
    }

    public SocialNetwork getNetwork() {
        return network;
    }

    public URI getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "SocialNetworkLink [network=" + network + ", link=" + link + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
