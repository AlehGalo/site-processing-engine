/**
 * 
 */
package com.jdev.collector.site.factory;

import com.jdev.collector.site.AbstractCollector;
import com.jdev.domain.entity.Credential;

/**
 * @author Aleh
 * 
 */
public interface ICollectorBuilder {

    /**
     * @return collector instance.
     */
    AbstractCollector createCollector();

    /**
     * @return
     */
    Credential getCredential();
}
