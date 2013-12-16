/**
 * 
 */
package com.jdev.crawler.core.store;

/**
 * @author Aleh
 * 
 */
public interface IFileStoreWritable extends IFileStore {

    /**
     * @param item
     *            to be added.
     */
    void add(IIndexedItem item);

    /**
     * @param item
     *            to be removed.
     * @param type
     *            type.
     */
    void remove(IIndexedItem item);

}
