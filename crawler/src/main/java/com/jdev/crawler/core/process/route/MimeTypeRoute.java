/**
 * 
 */
package com.jdev.crawler.core.process.route;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.functors.NotNullPredicate;
import org.apache.commons.collections.set.PredicatedSet;

import com.jdev.crawler.core.process.handler.MimeType;
import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.UnsupportedMimeTypeException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class MimeTypeRoute implements IRoute {

    /**
     * Acceptable types.
     */
    private final Set<MimeType> setOfAcceptableTypes;

    /**
     * @param types
     */
    @SuppressWarnings("unchecked")
    public MimeTypeRoute(final MimeType[] types) {
        Assert.notNull(types);
        setOfAcceptableTypes = PredicatedSet.decorate(new HashSet<MimeType>(),
                NotNullPredicate.getInstance());
        setOfAcceptableTypes.addAll(asList(types));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.process.route.IRoute#route(com.jdev.crawler.core
     * .process.model.IEntity)
     */
    @Override
    public void route(final IEntity entity) throws CrawlerException {
        if (!setOfAcceptableTypes.contains(entity.getMimeType())) {
            throw new UnsupportedMimeTypeException(entity.getMimeType()
                    + " is not supported for this processor.");
        }
    }

}
