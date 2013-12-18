/**
 * 
 */
package com.jdev.spider.core.extractor.deprecation;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Aleh
 * 
 */
public final class DeprecationFactory {

    /**
     * Hide constructor from public usage.
     */
    private DeprecationFactory() {
    }

    /**
     * @return deprecated.
     */
    public static IDeprecated createDeprecationRules() {
        Set<String> rules = new HashSet<String>();
        rules.add(".*javascript.*");
        rules.add(".*mailto.*");
        rules.add(".*@.*");
        return new RidiculousUrlDeprecated(rules);
    }

}
