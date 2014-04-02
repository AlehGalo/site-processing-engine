/**
 * 
 */
package com.jdev.crawler.core.selector.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import com.jdev.crawler.core.selector.ISelectUnit;
import com.jdev.crawler.core.selector.ISelectorResult;
import com.jdev.crawler.exception.SelectionException;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class HeaderEntityContentEncodingSelector extends EntitySelector {

    /**
     * @param unit
     */
    public HeaderEntityContentEncodingSelector(final ISelectUnit unit) {
        super(unit);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.selector.ISelector#select(java.lang.Object)
     */
    @Override
    public Collection<ISelectorResult> select(final HttpEntity content) throws SelectionException {
        Assert.notNull(content);
        List<ISelectorResult> list = new ArrayList<>();
        Header header = content.getContentEncoding();
        if (header != null) {
            list.addAll(getSelectionResultFromHeaderElements(header.getElements()));
        }
        return list;
    }

}