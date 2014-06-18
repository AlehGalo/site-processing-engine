/**
 * 
 */
package com.jdev.domain.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
@Table(name = "CREDENTIAL")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "CREDENTIAL_ID")) })
public class Credential extends AbstractIdentifiable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @Column(name = "USERNAME", nullable = false, columnDefinition = "VARCHAR(32)")
    private String username;

    /**
     * 
     */
    @Column(name = "PASSWORD", nullable = false, columnDefinition = "VARCHAR(32)")
    private String password;

    /**
     * 
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_SITE_ID")
    private Site site;

    /**
     * @param username
     * @param password
     */
    public Credential(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 
     */
    public Credential() {
    }

    /**
     * @return the username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the site
     */
    public final Site getSite() {
        return site;
    }

    /**
     * @param site
     *            the site to set
     */
    public final void setSite(final Site site) {
        this.site = site;
    }

}
