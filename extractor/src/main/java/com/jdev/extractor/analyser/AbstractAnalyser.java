/**
 * 
 */
package com.jdev.extractor.analyser;

import java.util.HashSet;
import java.util.Set;

import com.jdev.domain.adapter.IContent;
import com.jdev.extractor.extractor.IExtractor;

/**
 * @author Aleh
 * 
 */
public abstract class AbstractAnalyser implements IAnalyse {

    /**
     * 
     */
    protected Set<IExtractor<Set<String>, IContent>> setOfExtractors;

    /**
     * 
     */
    public AbstractAnalyser() {
	setOfExtractors = new HashSet<>();
    }

    /**
     * @param extractor
     */
    public void addExtractor(final IExtractor<Set<String>, IContent> extractor) {
	if (extractor != null) {
	    setOfExtractors.add(extractor);
	}
    }
}
