/**
 * 
 */
package com.jdev.crawler.core.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jdev.crawler.core.FileTypeEnum;

/**
 * @author Aleh Simple dummy empty file store.
 */
class DummyFileStore implements IFileStoreWritable {

    /**
     * Default list.
     */
    private static final List<IIndexedItem> DEFAULT_LIST = Collections
            .unmodifiableList(new ArrayList<IIndexedItem>());

    @Override
    public List<IIndexedItem> getItems(final FileTypeEnum fileType) {
        return DEFAULT_LIST;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public List<IIndexedItem> getAllItems() {
        return DEFAULT_LIST;
    }

    @Override
    public void add(final IIndexedItem item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(final IIndexedItem item) {
        throw new UnsupportedOperationException();
    }

}
