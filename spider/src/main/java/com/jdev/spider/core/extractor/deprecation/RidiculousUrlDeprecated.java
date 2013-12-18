/**
 * 
 */
package com.jdev.spider.core.extractor.deprecation;

import java.util.Set;

/**
 * @author Aleh
 * 
 */
public class RidiculousUrlDeprecated implements IDeprecated {

    /**
     * Deprecated pattern set.
     */
    private final Set<String> deprecatedHrefPatternSet;

    /**
     * 
     */
    public RidiculousUrlDeprecated(final Set<String> deprecationRegexpSet) {
        deprecatedHrefPatternSet = deprecationRegexpSet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.spider.core.extractor.deprecation.IDeprecated#isDeprecated
     * (java.lang.String)
     */
    @Override
    public boolean isNotDeprecated(final String uriPartOfUrl) {
        if (uriPartOfUrl != null) {
            for (String regexp : deprecatedHrefPatternSet) {
                if (uriPartOfUrl.matches(regexp)) {
                    return false;
                }
            }
        }
        return true;
    }

}
