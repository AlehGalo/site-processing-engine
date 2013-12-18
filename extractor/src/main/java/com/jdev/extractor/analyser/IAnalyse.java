/**
 * 
 */
package com.jdev.extractor.analyser;

import com.jdev.domain.Information;
import com.jdev.domain.adapter.IContent;

/**
 * @author Aleh
 * 
 */
public interface IAnalyse {

    /**
     * @param url
     * @param ii
     */
    void analyse(IContent url, Information ii);
}
