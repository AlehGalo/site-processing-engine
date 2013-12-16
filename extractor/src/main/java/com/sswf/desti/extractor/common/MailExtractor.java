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
public class MailExtractor implements IExtractor<Set<String>, IContent> {

    /**
     * 
     */
    private static final Pattern EMAIL_PATTERN = Pattern
	    .compile("[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");

    /**
     * 
     */
    public MailExtractor() {
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
	    final Matcher m = EMAIL_PATTERN.matcher(e.getContent());
	    while (m.find()) {
		set.add(m.group(0));
	    }
	}
	return set;
    }

}
