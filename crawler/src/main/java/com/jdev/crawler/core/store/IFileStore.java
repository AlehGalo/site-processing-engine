/**
 * 
 */
package com.jdev.crawler.core.store;

import java.util.List;

import com.jdev.crawler.core.FileTypeEnum;

/**
 * @author Aleh Files storage.
 */
public interface IFileStore {

    /**
     * @param fileType
     *            HTML, PDF, CSV or any supported.
     * @return list of items.
     */
    List<IIndexedItem> getItems(final FileTypeEnum fileType);

    /**
     * @return All items.
     */
    List<IIndexedItem> getAllItems();

    /**
     * @return true/false.
     */
    boolean isEmpty();

}
