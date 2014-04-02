/**
 * 
 */
package com.jdev.crawler.core.process.extract;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Aleh Validator for content. It's registry like style. All classes
 *         that are registered here will be processes with cookie store content.
 *         all other should be processed by String content.
 */
class CookieContentOfSelectorValidator implements IContentValidator {

    /**
     * Class names.
     */
    private final Set<String> setOfNames;

    /**
     * Simple constructor.
     */
    public CookieContentOfSelectorValidator() {
        setOfNames = new HashSet<>(2);
    }

    /**
     * Full the data to the registry.
     */
    void initRegistry() {
        setOfNames.add("com.jdev.crawler.core.selector.cookie.CookieSelector");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.extract.IContentValidator#isContentValid
     * (java.lang.Object)
     */
    @Override
    public boolean isContentValid(final Object obj) {
        if (obj != null) {
            setOfNames.contains(obj.getClass().getName());
        }
        return false;
    }

}
