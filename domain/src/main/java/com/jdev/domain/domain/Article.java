/**
 * 
 */
package com.jdev.domain.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "ARTICLE")
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "ARTICLE_ID")) })
public class Article extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Article's content.
     */
    @Column(name = "CONTENT", nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    /**
     * Article's content.
     */
    // @Column(name = "ORIGINAL_ARTICLE_URL", nullable = false, columnDefinition
    // = "VARCHAR(256)")
    @Transient
    private String originalArticleUrl;

    @Column(name = "TITLE", nullable = false, columnDefinition = "VARCHAR(256)")
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
    protected Article() {
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

    //
    // /**
    // * @return the site
    // */
    // public Site getSite() {
    // return site;
    // }
    //
    // /**
    // * @param site
    // * the site to set
    // */
    // public void setSite(final Site site) {
    // this.site = site;
    // }

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