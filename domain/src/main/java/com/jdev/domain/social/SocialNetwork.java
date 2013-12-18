package com.jdev.domain.social;

/**
 * @author Al
 */
public enum SocialNetwork {
    GOOGLE_PLUS("GooglePlus"), FACEBOOK("Facebook"), TWITTER("Twitter"), PINTEREST("Pinterest"), INSTAGRAM(
            "Instagram");

    private final String name;

    private SocialNetwork(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
