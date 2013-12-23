/**
 * 
 */
package com.jdev.crawler.core.store;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.jdev.crawler.core.FileTypeEnum;
import com.jdev.crawler.util.Assert;

/**
 * @author Aleh
 * 
 */
public class IndexedItem implements IIndexedItem {

    /**
     * Default serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Generated_part
     */
    private final String fileName;

    /**
     * Not required string.
     */
    private final String description;

    /**
     * HTML, PDF, CSV.
     */
    private final FileTypeEnum fileType;

    /**
     * ID of the job.
     */
    private final String jobId;

    /**
     * @param fileType
     * @param fileName
     *            fileName to the file.
     */
    public IndexedItem(final FileTypeEnum fileType, final String fileName) {
        this(fileType, fileName, null);
    }

    /**
     * @param fileTypeIn
     * @param filePath
     *            fileName to the file.
     */
    public IndexedItem(final FileTypeEnum fileType, final String fileName, final String jobId) {
        this(fileType, fileName, jobId, null);
    }

    /**
     * @param fileType
     * @param fileName
     *            fileName to the file.
     */
    public IndexedItem(final FileTypeEnum fileType, final String fileName, final String jobId,
            final String description) {
        Assert.notNull(fileType);
        Assert.hasLength(fileName);
        this.fileType = fileType;
        this.fileName = fileName;
        this.jobId = jobId;
        this.description = description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jdev.crawler.core.process.IDescription#getDescription()
     */
    @Override
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cinergy.crawler.core.store.IIndexedItem#getPath()
     */
    @Override
    public String getFileName() {
        return fileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cinergy.crawler.core.store.IIndexedItem#getType()
     */
    @Override
    public FileTypeEnum getType() {
        return fileType;
    }

    @Override
    public String getJobId() {
        return jobId;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof IndexedItem == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        IndexedItem item = (IndexedItem) obj;
        return new EqualsBuilder().append(item.fileName, fileName).append(item.fileType, fileType)
                .append(item.jobId, jobId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(fileName).append(fileType).append(jobId).hashCode();
    }
}
