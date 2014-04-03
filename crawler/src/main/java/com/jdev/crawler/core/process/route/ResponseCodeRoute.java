/**
 * 
 */
package com.jdev.crawler.core.process.route;

import com.jdev.crawler.core.process.model.IEntity;
import com.jdev.crawler.exception.CrawlerException;
import com.jdev.crawler.exception.InvalidPageException;
import com.jdev.crawler.exception.ServerErrorPageException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class ResponseCodeRoute implements IRoute {

    /**
     * 
     */
    public ResponseCodeRoute() {
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
        Assert.notNull(entity);
        int resultStatusCode = entity.getStatusCode();
        int firstNumberOfResultStatusCode = (resultStatusCode / 100);
        switch (firstNumberOfResultStatusCode) {
        case 4:
            throw new InvalidPageException("Page you are requested is not valid error code: "
                    + resultStatusCode);
        case 5:
            throw new ServerErrorPageException("Page you are requested is not valid error code: "
                    + resultStatusCode);
        }
    }

}
