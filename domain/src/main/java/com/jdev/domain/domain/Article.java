/**
 * 
 */
package com.jdev.domain.domain;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "ARTICLE")
@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "ARTICLE_ID")) })
@SqlResultSetMappings(value = {
        @SqlResultSetMapping(name = "implicitArticle", entities = @EntityResult(entityClass = Article.class)),
        @SqlResultSetMapping(name = "previewArticle", entities = @EntityResult(entityClass = Article.class, fields = {
                @FieldResult(name = "title", column = "TITLE"),
                @FieldResult(name = "preview", column = "PREVIEW") })) })
@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "findAllArticles", query = "select * from article a", resultSetMapping = "implicitArticle"),
        @NamedNativeQuery(name = "findByTitle", query = "select * from article a where a.title=:title", resultClass = Article.class),
        @NamedNativeQuery(name = "findAllArticlePreviews", query = "select a.ARTICLE_ID, a.PREVIEW, a.TITLE, a.FK_IMAGE_ID from article a", resultClass = Article.class) })
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
    @Column(name = "ORIGINAL_ARTICLE_URL", nullable = false, columnDefinition = "VARCHAR(256)")
    private String originalArticleUrl;

    /**
     * Resource source.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SITE_ID")
    private Site site;

    /**
     * Resource source.
     */
    @ManyToOne(optional = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_IMAGE_ID")
    private Image image;

    /**
     * Resource source.
     */
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_TITLE_ID")
    private Title title;

    /**
     * Recommendation.
     */
    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
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

    /**
     * @return the site
     */
    public Site getSite() {
        return site;
    }

    /**
     * @param site
     *            the site to set
     */
    public void setSite(final Site site) {
        this.site = site;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image
     *            the image to set
     */
    public void setImage(final Image image) {
        this.image = image;
    }

    /**
     * @return the title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(final Title title) {
        this.title = title;
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

}