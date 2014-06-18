/**
 * 
 */
package com.jdev.domain.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Aleh
 * 
 */
@Entity
@Table(name = "RECOMMENDATION")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "RECOMMENDATION_ID")) })
public class Recommendation extends AbstractIdentifiable {

    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Article.
     */
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "FK_ARTICLE_ID")
    private Article article;

    /**
     * Cannot be null
     */
    @Column(nullable = false, name = "VOTE")
    private Boolean vote;

    /**
     * 
     */
    public Recommendation() {
    }

    /**
     * @return the article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * @param article
     *            the article to set
     */
    public void setArticle(final Article article) {
        this.article = article;
    }

    /**
     * @return the vote
     */
    public Boolean getVote() {
        return vote;
    }

    /**
     * @param vote
     *            the vote to set
     */
    public void setVote(final Boolean vote) {
        this.vote = vote;
    }

}