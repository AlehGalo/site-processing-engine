/**
 * 
 */
package com.jdev.crawler.core.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import com.jdev.crawler.core.FileTypeEnum;
import com.jdev.crawler.core.user.IStorageUniqueKey;

/**
 * @author Aleh
 * 
 */
public class TestFileStorage {

    /**
     * 
     */
    private static final String KEY = "STORAGE_KEY";

    /**
     * IIndexedItem item.
     */
    private static final IndexedItem ITEM = new IndexedItem(FileTypeEnum.HTML, KEY, KEY);

    /**
     * Unique key.
     */
    private static final IStorageUniqueKey UNIQUE_KEY = new IStorageUniqueKey() {

        @Override
        public String getUniqueKey() {
            return KEY;
        }
    };

    private FileStorage initFileStorage() {
        FileStorage fileStorage = new FileStorage(KEY);
        IFileStoreWritable fileStoreWritable = fileStorage.getFileStore(UNIQUE_KEY);
        fileStoreWritable.add(ITEM);
        return fileStorage;
    }

    /**
     * Insert value.
     */
    @Test
    public void testInsertValue() {
        FileStorage fileStorage = initFileStorage();
        assertion(fileStorage);
        IFileStoreWritable fileStoreWritable = fileStorage.getFileStore(UNIQUE_KEY);
        fileStoreWritable.add(SerializationUtils.clone(ITEM));
        assertion(fileStorage);
    }

    /**
     * Insert value.
     */
    @Test
    public void testRemoveValue() {
        FileStorage fileStorage = initFileStorage();
        IFileStoreWritable fw = fileStorage.getFileStore(UNIQUE_KEY);
        fw.remove(ITEM);
        assertion1(fileStorage);
    }

    /**
     * @param fileStorage
     */
    private void assertion(final FileStorage fileStorage) {
        assertFalse(fileStorage.isEmpty());
        assertTrue(fileStorage.getName().equals(KEY));
        assertEquals(fileStorage.getAllFileStore().size(), 1);
        assertFalse(fileStorage.getFileStore(UNIQUE_KEY).isEmpty());
        assertEquals(fileStorage.getFileStore(UNIQUE_KEY).getItems(FileTypeEnum.HTML).size(), 1);
        assertTrue(fileStorage.getFileStore(UNIQUE_KEY).getItems(FileTypeEnum.HTML).get(0)
                .equals(ITEM));
    }

    /**
     * @param fileStorage
     */
    private void assertion1(final FileStorage fileStorage) {
        assertTrue(fileStorage.isEmpty());
        assertEquals(fileStorage.getAllFileStore().size(), 1);
        assertTrue(fileStorage.getFileStore(UNIQUE_KEY).isEmpty());
        assertEquals(fileStorage.getFileStore(UNIQUE_KEY).getItems(FileTypeEnum.HTML).size(), 0);
        assertTrue(fileStorage.getFileStore(UNIQUE_KEY).getItems(FileTypeEnum.HTML).isEmpty());
    }
}
