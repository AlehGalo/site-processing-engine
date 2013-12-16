/**
 * 
 */
package com.jdev.crawler.core.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleh
 * 
 */
public class FileStorageAsync extends FileStorage {

    /**
     * @param fileStorageName
     */
    public FileStorageAsync(final String fileStorageName) {
        super(fileStorageName);
    }

    @Override
    Map<String, IFileStoreWritable> createStorageDataHolder() {
        return new ConcurrentHashMap<>();
    }

    @Override
    IFileStoreWritable createFileStoreWritable() {
        return new FileStoreWritableAsync();
    }

}