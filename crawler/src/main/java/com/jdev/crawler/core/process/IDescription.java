/**
 * 
 */
package com.jdev.crawler.core.process;

import java.io.Serializable;

/**
 * @author Aleh Description of the module.
 */
public interface IDescription extends Serializable {

    /**
     * @return textual human readable description.
     */
    String getDescription();
}
