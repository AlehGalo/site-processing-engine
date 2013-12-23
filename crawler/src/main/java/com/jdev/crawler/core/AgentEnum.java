/**
 * 
 */
package com.jdev.crawler.core;

/**
 * @author Aleh Changeable agents.
 */
public enum AgentEnum {
    /**
     * Google chrome user agent simation.
     */
    CHROME_USER_AGENT(
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11"),

    /**
     * Firefox user agent simuation.
     */
    FIREFOX_USER_AGENT("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:17.0) Gecko/20100101 Firefox/17.0"),

    /**
     * IE user agent simuation.
     */
    IE_USER_AGENT("Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko");

    /**
     * Text of agent.
     */
    private String agentText;

    /**
     * @param text
     *            of the agent.
     */
    private AgentEnum(String text) {
        agentText = text;
    }

    /**
     * @return agent text.
     */
    public String getAgentText() {
        return agentText;
    }
};
