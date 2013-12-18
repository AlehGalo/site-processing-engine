/**
 * 
 */
package com.jdev.domain.address;

/**
 * @author Aleh Sample address: 7850 Beach Boulevard Buena Park, CA 90620.
 */
public interface IAddress {

    /**
     * @return state. (CA)
     */
    String getState();

    /**
     * @return street (Beach Boulevard).
     */
    String getStreet();

    /**
     * @return postal code. (90620)
     */
    String getPostalCode();

    /**
     * @return city.
     */
    String getCity();

    /**
     * @return home number. (7850)
     */
    String getHomeNumber();
}
