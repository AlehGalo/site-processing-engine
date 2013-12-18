/**
 * 
 */
package com.jdev.domain;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.jdev.domain.address.IAddress;
import com.jdev.domain.mail.IMail;
import com.jdev.domain.price.IPrice;
import com.jdev.domain.social.ISocial;
import com.jdev.domain.social.Social;

/**
 * @author Aleh
 * 
 */
public class Information {

    /**
     * 
     */
    private URL homeUrl;

    /**
     * 
     */
    private Set<String> phoneNumbers;

    /**
     * 
     */
    private Set<IMail> mails;

    /**
     * 
     */
    private Set<IAddress> addresses;

    /**
     * 
     */
    private Set<IPrice> setOfPrices;

    /**
     * 
     */
    @Deprecated
    private Set<String> setOfPricesTemp;

    /**
     * 
     */
    private ISocial social;

    /**
     * @param homeUrl
     * 
     */
    public Information(final String homeUrl) {
        try {
            setHomeUrl(new URL(homeUrl));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        mails = new HashSet<>();
        addresses = new HashSet<>();
        phoneNumbers = new HashSet<>();
        social = new Social();
        setOfPrices = new HashSet<>();
        setOfPricesTemp = new HashSet<>();
    }

    /**
     * @return the homeUrl
     */
    public URL getHomeUrl() {
        return homeUrl;
    }

    /**
     * @param homeUrl
     *            the homeUrl to set
     */
    public void setHomeUrl(final URL homeUrl) {
        this.homeUrl = homeUrl;
    }

    /**
     * @return the phoneNumbers
     */
    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * @param phoneNumbers
     *            the phoneNumbers to set
     */
    public void setPhoneNumbers(final Set<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    /**
     * @return the mails
     */
    public Set<IMail> getMails() {
        return mails;
    }

    /**
     * @param mails
     *            the mails to set
     */
    @Deprecated
    public void setMails(final Set<IMail> mails) {
        this.mails = mails;
    }

    /**
     * @return the addresses
     */
    public Set<IAddress> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses
     *            the addresses to set
     */
    public void setAddresses(final Set<IAddress> addresses) {
        this.addresses = addresses;
    }

    /**
     * @return the social
     */
    public ISocial getSocial() {
        return social;
    }

    /**
     * @param social
     *            the social to set
     */
    public void setSocial(final Social social) {
        this.social = social;
    }

    /**
     * @return the setOfPrices
     */
    public Set<IPrice> getSetOfPrices() {
        return setOfPrices;
    }

    /**
     * @param setOfPrices
     *            the setOfPrices to set
     */
    public void setSetOfPrices(final Set<IPrice> setOfPrices) {
        this.setOfPrices = setOfPrices;
    }

    /**
     * @return the setOfPricesTemp
     */
    public Set<String> getSetOfPricesTemp() {
        return setOfPricesTemp;
    }

    /**
     * @param setOfPricesTemp
     *            the setOfPricesTemp to set
     */
    public void setSetOfPricesTemp(final Set<String> setOfPricesTemp) {
        this.setOfPricesTemp = setOfPricesTemp;
    }
}
