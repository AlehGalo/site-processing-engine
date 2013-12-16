/**
 * 
 */
package com.jdev.crawler.core.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.jdev.crawler.core.user.IStorageUniqueKey;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
abstract class AbstractFileStorage implements IFileStorage {

    /**
     * Pattern for root name checking.
     */
    private static final Pattern FILE_STORAGE_NAME_PATTERN = Pattern.compile("[a-zA-z0-9_]+");

    /**
     * Map of items.
     */
    private final Map<String, IFileStoreWritable> mapByUniqueKey;

    /**
     * root name of storage.
     */
    private final String storageName;

    /**
     * @param fileStorageName
     *            file store name.
     */
    public AbstractFileStorage(final String fileStorageName) {
        Assert.isTrue(isFileStorageNameCorrect(fileStorageName),
                "Incorrect file storage name. It doesn't follow the pattern.");
        this.storageName = fileStorageName;
        mapByUniqueKey = createStorageDataHolder();
    }

    /**
     * @return storage data holder.
     */
    abstract Map<String, IFileStoreWritable> createStorageDataHolder();

    /**
     * @return file store writable.
     */
    abstract IFileStoreWritable createFileStoreWritable();

    /**
     * Check the file storage name for pattern matching.
     * 
     * @param fileStorageName
     *            name of the file storage.
     * @return true/false.
     */
    private boolean isFileStorageNameCorrect(final String fileStorageName) {
        return StringUtils.isNotBlank(fileStorageName)
                && FILE_STORAGE_NAME_PATTERN.matcher(fileStorageName).matches();
    }

    @Override
    public IFileStoreWritable getFileStore(final IStorageUniqueKey key) {
        if (key == null || StringUtils.isBlank(key.getUniqueKey())) {
            return new DummyFileStore();
        }
        IFileStoreWritable fileStoreWritable = mapByUniqueKey.get(key.getUniqueKey());
        if (fileStoreWritable == null) {
            fileStoreWritable = createFileStoreWritable();
            mapByUniqueKey.put(key.getUniqueKey(), fileStoreWritable);
        }
        return fileStoreWritable;
    }

    @Override
    public boolean isEmpty() {
        for (final IFileStoreWritable fileStore : mapByUniqueKey.values()) {
            if (!fileStore.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Collection<IFileStoreWritable> getAllFileStore() {
        final List<IFileStoreWritable> list = new ArrayList<>();
        for (final IFileStoreWritable fileStore : mapByUniqueKey.values()) {
            list.add(fileStore);
        }
        return Collections.<IFileStoreWritable> unmodifiableList(list);
    }

    @Override
    public String getName() {
        return this.storageName;
    }
}
