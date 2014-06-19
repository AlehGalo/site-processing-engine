/**
 * 
 */
package com.jdev.domain.entity;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "ARTICLE", indexes = { @Index(columnList = "TITLE") })
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "ARTICLE_ID")) })
public class Article extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Article's content.
     */
    @Column(name = "CONTENT", nullable = false, columnDefinition = "MEDIUMTEXT")
    @NotEmpty
    private String content;

    /**
     * Article's content.
     */
    @Column(name = "ORIGINAL_ARTICLE_URL", nullable = false, columnDefinition = "VARCHAR(256)", unique = true)
    @NotEmpty
    @URL
    // TODO: add validation
    private String originalArticleUrl;

    /**
     * Title.
     */
    @Column(name = "TITLE", nullable = false, columnDefinition = "VARCHAR(255)")
    @NotEmpty
    private String title;

    /**
     * Resource source.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_JOB_ID")
    private Job job;

    /**
     * Recommendation.
     */
    // @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    @Transient
    private Set<Recommendation> recommendationSet;

    /**
     * Persistence layer using this constructor.
     */
    public Article() {
    }

    /**
     * @param content
     * @param originalArticleUrl
     * @param title
     */
    public Article(final String content, final String originalArticleUrl, final String title) {
        this.content = content;
        this.originalArticleUrl = originalArticleUrl;
        this.title = title;
    }

    /**
     * @param originalArticleUrl
     * @param title
     */
    public Article(final String originalArticleUrl, final String title) {
        this.originalArticleUrl = originalArticleUrl;
        this.title = title;
    }

    /**
     * Mainly used constructor with all the parameters.
     * 
     * @param title
     *            article's title.
     * @param preview
     *            article's preview.
     * @param content
     *            article's content.
     * @param dateTime
     *            pickup gathering.
     */
    public Article(final String content) {
        this.content = content;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(final String content) {
        this.content = content;
    }

    /**
     * @return the originalArticleUrl
     */
    public final String getOriginalArticleUrl() {
        return originalArticleUrl;
    }

    /**
     * @param originalArticleUrl
     *            the originalArticleUrl to set
     */
    public final void setOriginalArticleUrl(final String originalArticleUrl) {
        this.originalArticleUrl = originalArticleUrl;
    }

    /**
     * @return the recommendationSet
     */
    public Set<Recommendation> getRecommendationSet() {
        return recommendationSet;
    }

    /**
     * @param recommendationSet
     *            the recommendationSet to set
     */
    public void setRecommendationSet(final Set<Recommendation> recommendationSet) {
        this.recommendationSet = recommendationSet;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return the job
     */
    public final Job getJob() {
        return job;
    }

    /**
     * @param job
     *            the job to set
     */
    public final void setJob(final Job job) {
        this.job = job;
    }

}