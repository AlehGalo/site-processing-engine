/**
 * 
 */
package com.sswf.desti.extractor.social;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.sswf.desti.extractor.IPusher;

/**
 * @author Aleh
 * 
 * @param <T>
 * @param <E>
 */
abstract class AbstractSetOfStringsPusher<T extends Set<String>, E> implements
	IPusher<T, E> {
    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.extractor.IPuller#pullData(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public final void pushData(final T t, final E e) {
	if (t != null && !t.isEmpty() && e != null) {
	    try {
		fillBean(e, createURI((String) CollectionUtils.get(t, 0)));
	    } catch (final URISyntaxException e1) {
		// TODO: got wrong
	    }
	}
    }

    /**
     * @param e
     * @param data
     */
    public abstract void fillBean(E e, URI data);

    /**
     * @param source
     * @return
     * @throws URISyntaxException
     */
    protected final URI createURI(final String source)
	    throws URISyntaxException {
	return new URI(source);
    }
}
