/**
 * 
 */
package com.jdev.crawler.core.store;

import java.util.Collection;

import com.jdev.crawler.core.user.IStorageUniqueKey;

/**
 * @author Aleh
 * 
 */
public interface IFileStorage {

    /**
     * @return File store for mobile number.
     */
    IFileStoreWritable getFileStore(IStorageUniqueKey number);

    /**
     * Collection of file stores.
     */
    Collection<IFileStoreWritable> getAllFileStore();

    /**
     * @return true/false.
     */
    boolean isEmpty();

    /**
     * @return file storage name.
     */
    String getName();
}
