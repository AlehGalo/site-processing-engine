/**
 * 
 */
package com.sswf.desti.analyser;

import com.sswf.desti.domain.Information;
import com.sswf.desti.domain.adapter.IContent;

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
