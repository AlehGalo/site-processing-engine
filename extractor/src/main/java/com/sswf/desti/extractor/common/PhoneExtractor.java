/**
 * 
 */
package com.sswf.desti.extractor.common;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.extractor.IExtractor;

/**
 * @author Aleh
 * 
 */
public class PhoneExtractor implements IExtractor<Set<String>, IContent> {

    /**
     * 
     */
    private static final Pattern PHONE_PATTERN = Pattern
	    .compile("\\(\\d{3}\\)\\s+\\d{3}\\-\\d{4}");

    /**
     * 
     */
    public PhoneExtractor() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.extractor.IExtractor#extract(java.lang.Object)
     */
    @Override
    public Set<String> extract(final IContent e) {
	final Set<String> set = new HashSet<>();
	if (e != null && StringUtils.isNotBlank(e.getContent())) {
	    final Matcher m = PHONE_PATTERN.matcher(e.getContent());
	    while (m.find()) {
		set.add(m.group(0));
	    }
	}
	return set;
    }

}
