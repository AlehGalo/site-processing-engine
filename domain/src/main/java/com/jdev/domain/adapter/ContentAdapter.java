/**
 * 
 */
package com.jdev.domain.adapter;

import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Aleh
 * 
 */
public class ContentAdapter implements IContent {

    @Override
    public String getUrl() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getContent() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getText() {
        return StringUtils.EMPTY;
    }

    @Override
    public void resetStrings() {
    }

    @Override
    public Set<IContent> getOutcomingLinks() {
        return Collections.emptySet();
    }

}
