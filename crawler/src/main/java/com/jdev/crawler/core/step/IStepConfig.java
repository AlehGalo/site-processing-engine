/**
 *
 */
package com.jdev.crawler.core.step;

import java.util.Collection;

import com.jdev.crawler.core.selector.ISelector;

/**
 * @author Aleh
 */
public interface IStepConfig {

    String getUrl();

    Collection<ISelector> getParameters();

    String getMethod();
}
