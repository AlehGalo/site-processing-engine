/**
 * 
 */
package com.jdev.crawler.core.store;

import com.jdev.crawler.core.FileTypeEnum;
import com.jdev.crawler.core.process.IDescription;

/**
 * @author Aleh
 * 
 */
public interface IIndexedItem extends IDescription {

    /**
     * @return name of the file.
     */
    String getFileName();

    /**
     * @return File type.
     */
    FileTypeEnum getType();

    /**
     * @return job id.
     */
    String getJobId();
}
