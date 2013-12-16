/**
 * 
 */
package com.jdev.crawler.core.store;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.jdev.crawler.core.FileType;

/**
 * @author Aleh
 * 
 */
public class FileStoreWritableAsync extends FileStoreWritable {

    @Override
    Map<FileType, Set<IIndexedItem>> createMapWithAssociatedFileType() {
        return new ConcurrentHashMap<>();
    }

    @Override
    Map<String, Set<IIndexedItem>> createMapWithSetOfItems() {
        return new ConcurrentHashMap<>();
    }

    @Override
    Set<IIndexedItem> createSet() {
        return new CopyOnWriteArraySet<>();
    }
}
