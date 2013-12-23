/**
 *
 */
package com.jdev.crawler.core.user;

import java.util.UUID;

import com.jdev.crawler.util.Assert;

/**
 * @author Aleh All the data about the user.
 */
public class UserData implements IUserData {

    /**
     * User name.
     */
    private String login;

    /**
     * Password of th user.
     */
    private String password;

    /**
     * Phone number.
     */
    private String phoneNumber;

    /**
     * Security code as usual it can be pin.
     */
    private String securityCode;

    /**
     * Company.
     */
    private ICompany company;

    /**
     * 
     */
    private final String uuid = UUID.randomUUID().toString();

    /**
     * Empty body constructor.
     */
    public UserData() {
    }

    /**
     * @param name
     *            login name.
     * @param pass
     *            password.
     */
    public UserData(final String name, final String pass) {
        setLogin(name);
        setPassword(pass);
    }

    /**
     * @param login
     *            user's login.
     * @param password
     *            user's password.
     * @param phoneNumber
     *            user's phone number.
     * @param securityCode
     *            user's security code.
     * @param company
     *            company.
     */
    public UserData(final String login, final String password, final String phoneNumber,
            final String securityCode, final ICompany company) {
        setLogin(login);
        setPassword(password);
        setPhoneNumber(phoneNumber);
        setSecurityCode(securityCode);
        setCompany(company);
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    /**
     * @return the login
     */
    @Override
    public String getLogin() {
        return login;
    }

    /**
     * @param login
     *            the login to set
     */
    public void setLogin(final String login) {
        Assert.hasLength(login, "Login cannot be empty or null.");
        this.login = login;
    }

    /**
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String getUniqueKey() {
        return phoneNumber;
    }

    @Override
    public String getSecurityCode() {
        return this.securityCode;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @param securityCode
     *            the securityCode to set
     */
    public void setSecurityCode(final String securityCode) {
        this.securityCode = securityCode;
    }

    @Override
    public ICompany getCompany() {
        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany(final ICompany company) {
        this.company = company;
    }
}