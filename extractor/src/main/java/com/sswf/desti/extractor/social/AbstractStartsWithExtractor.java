/**
 * 
 */
package com.sswf.desti.extractor.social;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.extractor.IExtractor;

/**
 * @author Aleh
 * 
 */
abstract class AbstractStartsWithExtractor implements
	IExtractor<Set<String>, IContent> {

    /**
     * 
     */
    private Set<String> startsWith;

    /**
     * 
     */
    public AbstractStartsWithExtractor() {
	startsWith = new HashSet<>();
    }

    @Override
    public Set<String> extract(final IContent e) {
	if (e != null && e.getUrl() != null) {
	    for (final String s : startsWith) {
		if (e.getUrl().startsWith(s)) {
		    return Collections.singleton(e.getUrl());
		}
	    }
	}
	return Collections.<String> emptySet();
    }

    protected final void addStartsWithPattern(final String source) {
	if (StringUtils.isNotBlank(source)) {
	    startsWith.add(source);
	}
    }
}