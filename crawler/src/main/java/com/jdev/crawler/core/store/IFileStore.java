/**
 * 
 */
package com.jdev.crawler.core.store;

import java.util.List;

import com.jdev.crawler.core.FileType;

/**
 * @author Aleh Files storage.
 */
public interface IFileStore {

    /**
     * @param fileType
     *            HTML, PDF, CSV or any supported.
     * @return list of items.
     */
    List<IIndexedItem> getItems(final FileType fileType);

    /**
     * @param fileType
     *            HTML, PDF, CSV or any supported.
     * @param jobId
     *            unique id for the job.
     * @return list of items.
     */
    List<IIndexedItem> getItems(final FileType fileType, final String jobId);

    /**
     * @return All items.
     */
    List<IIndexedItem> getAllItems();

    /**
     * @return true/false.
     */
    boolean isEmpty();

}
