/**
 * 
 */
package com.jdev.crawler.core.store;

import com.jdev.crawler.core.FileType;
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
    FileType getType();

    /**
     * @return job id.
     */
    String getJobId();
}
