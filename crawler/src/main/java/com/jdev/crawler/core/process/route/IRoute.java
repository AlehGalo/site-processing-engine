/**
 * 
 */
package com.jdev.crawler.core.process.route;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;

/**
 * @author Aleh
 * 
 */
public interface IRoute {

    /**
     * @param entity
     *            of the request.
     */
    void route(IEntity entity) throws CrawlerException;

}
