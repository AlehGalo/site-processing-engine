/**
 * 
 */
package com.jdev.crawler.core.store;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleh
 * 
 */
public class FileStorage extends AbstractFileStorage {

    /**
     * @param fileStorageName
     */
    public FileStorage(final String fileStorageName) {
        super(fileStorageName);
    }

    @Override
    Map<String, IFileStoreWritable> createStorageDataHolder() {
        return new HashMap<>();
    }

    @Override
    IFileStoreWritable createFileStoreWritable() {
        return new FileStoreWritable();
    }

}