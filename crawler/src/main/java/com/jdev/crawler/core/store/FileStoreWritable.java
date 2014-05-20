/**
 * 
 */
package com.jdev.crawler.core.store;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.jdev.crawler.core.FileTypeEnum;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class FileStoreWritable implements IFileStoreWritable {

    /**
     * Index format.
     */
    private static String INDEX_FORMAT = "{0}_{1}_{2}";

    /**
     * Map of all items sorted by file type.
     */
    private final Map<FileTypeEnum, Set<IIndexedItem>> map;

    /**
     * Map of indexed items.
     */
    private final Map<String, Set<IIndexedItem>> mapIndexed;

    /**
     * 
     */
    private final Set<IIndexedItem> checkSet;

    /**
     * 
     */
    public FileStoreWritable() {
        map = createMapWithAssociatedFileType();
        mapIndexed = createMapWithSetOfItems();
        checkSet = createSet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see cinergy.crawler.core.store.IFileStore#getItems(cinergy.crawler.core.
     * FileTypeEnum)
     */
    @Override
    public List<IIndexedItem> getItems(final FileTypeEnum fileType) {
        return getNotNullNonModifiableItemsList(map.get(fileType));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * cinergy.crawler.core.store.IFileStoreWritable#add(cinergy.crawler.core
     * .result.IIndexedItem)
     */
    @Override
    public void add(final IIndexedItem item) {
        if (item == null) {
            return;
        }
        if (checkSet.add(item)) {
            addToSortedMap(item);
            addToIndexedMap(item);
        }
    }

    private void addToIndexedMap(final IIndexedItem item) {
        final String index = createIndexString(item);
        if (StringUtils.isBlank(index)) {
            return;
        }
        final Set<IIndexedItem> hashSet = createSet();
        hashSet.add(item);
        final Set<IIndexedItem> set = mapIndexed.get(index);
        if (set == null) {
            mapIndexed.put(index, hashSet);
        } else {
            set.addAll(hashSet);
        }
    }

    /**
     * @param item
     *            item.
     * @return index string.
     */
    private String createIndexString(final IIndexedItem item) {
        return MessageFormat.format(INDEX_FORMAT, item.getType().name().toLowerCase(),
                item.getJobId(), item.getFileName());
    }

    /**
     * @param item
     *            indexed item.
     */
    private void addToSortedMap(final IIndexedItem item) {
        final Set<IIndexedItem> set = createSet();
        set.add(item);
        final FileTypeEnum type = item.getType();
        Assert.notNull(type, "File type is not set for item.");
        final Set<IIndexedItem> setOfItems = map.get(type);
        if (setOfItems == null) {
            map.put(type, set);
        } else {
            setOfItems.addAll(set);
        }
    }

    /**
     * @param set
     *            list or null.
     * @return List empty or not.
     */
    private List<IIndexedItem> getNotNullNonModifiableItemsList(final Set<IIndexedItem> set) {
        return set == null ? new ArrayList<IIndexedItem>() : Collections
                .<IIndexedItem> unmodifiableList(new ArrayList<IIndexedItem>(set));
    }

    @Override
    public boolean isEmpty() {
        return checkSet.isEmpty();
    }

    @Override
    public List<IIndexedItem> getAllItems() {
        final Set<IIndexedItem> set = createSet();
        for (final Set<IIndexedItem> item : map.values()) {
            set.addAll(item);
        }
        return getNotNullNonModifiableItemsList(set);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jdev.crawler.core.store.IFileStoreWritable#remove(com.jdev.crawler
     * .core.store.IIndexedItem)
     */
    @Override
    public void remove(final IIndexedItem item) {
        if (item != null && item.getType() != null) {
            map.get(item.getType()).remove(item);
            checkSet.remove(item);
            mapIndexed.get(createIndexString(item)).remove(item);
        }
    }

    /**
     * @return set implementation.
     */
    Set<IIndexedItem> createSet() {
        return new HashSet<>();
    }

    /**
     * @return map instance.
     */
    Map<String, Set<IIndexedItem>> createMapWithSetOfItems() {
        return new HashMap<>();
    }

    /**
     * @return map instance.
     */
    Map<FileTypeEnum, Set<IIndexedItem>> createMapWithAssociatedFileType() {
        return new HashMap<>();
    }

}
