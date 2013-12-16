/**
 *
 */
package com.jdev.crawler.core;

import com.jdev.crawler.core.process.IProcess;
import com.jdev.crawler.core.user.IUserData;

/**
 * @author Aleh
 */
public interface IStepBuilder {
    /**
     * @param userData
     * @return
     */
    IProcess buildSteps(IUserData userData);
}
