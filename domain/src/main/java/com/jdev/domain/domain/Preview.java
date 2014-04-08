/**
 * 
 */
package com.jdev.domain.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "PREVIEW")
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "PREVIEW_ID")) })
public class Preview extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Column(columnDefinition = "TEXT", nullable = false, name = "PREVIEW")
    private String preview;

    /**
     * 
     */
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_TITLE_ID")
    private Title title;

    /**
     * 
     */
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ARTICLE_ID")
    private Article article;

    /**
     * 
     */
    @ManyToOne(optional = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_SCALED_IMAGE_ID")
    private ScaledImage scaledImage;

    /**
     * 
     */
    protected Preview() {
    }

    /**
     * @param preview
     */
    public Preview(final String preview) {
        this.preview = preview;
    }

    /**
     * @return the preview
     */
    public final String getPreview() {
        return preview;
    }

    /**
     * @param preview
     *            the preview to set
     */
    public final void setPreview(final String preview) {
        this.preview = preview;
    }

    /**
     * @return the title
     */
    public final Title getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public final void setTitle(final Title title) {
        this.title = title;
    }

    /**
     * @return the article
     */
    public final Article getArticle() {
        return article;
    }

    /**
     * @param article
     *            the article to set
     */
    public final void setArticle(final Article article) {
        this.article = article;
    }

    /**
     * @return the scaledImage
     */
    public final ScaledImage getScaledImage() {
        return scaledImage;
    }

    /**
     * @param scaledImage
     *            the scaledImage to set
     */
    public final void setScaledImage(final ScaledImage scaledImage) {
        this.scaledImage = scaledImage;
    }
}
