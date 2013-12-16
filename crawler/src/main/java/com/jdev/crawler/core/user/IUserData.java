/**
 *
 */
package com.jdev.crawler.core.user;


/**
 * @author Aleh
 */
public interface IUserData extends IStorageUniqueKey {

    /**
     * @return UUID
     */
    String getUUID();

    /**
     * @return login or null;
     */
    String getLogin();

    /**
     * @return password or null.
     */
    String getPassword();

    /**
     * @return security code if required. Null otherwise.
     */
    String getSecurityCode();

    /**
     * @return company id.
     */
    ICompany getCompany();
}
