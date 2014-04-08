/**
 * 
 */
package com.jdev.domain.domain.util;

import static com.google.common.net.InetAddresses.forString;
import static com.google.common.net.InetAddresses.isInetAddress;

import java.math.BigInteger;

/**
 * @author Aleh
 * 
 */
public final class InetAddressUtil {

    /**
     * Max address value for IPv6 address.
     */
    public static final BigInteger MAX_ADDRESS = new BigInteger(
            "340282366920938463463374607431768211455");

    /**
     * Min inet address.
     */
    public static final BigInteger MIN_ADDRESS = new BigInteger("0");

    /**
     * 
     */
    private InetAddressUtil() {
    }

    /**
     * @param ipAddress
     *            ipv6/ipv4 address.
     * @return number.
     */
    public static BigInteger getNumberOfIpAddress(final String ipAddress) {
        if (isInetAddress(ipAddress)) {
            return new BigInteger(1, forString(ipAddress).getAddress());
        } else {
            throw new IllegalArgumentException(ipAddress);
        }
    }

    /**
     * @param bigInteger
     *            number.
     * @return true/false.
     */
    public static boolean isValidAddress(final BigInteger bigInteger) {
        return bigInteger != null & MAX_ADDRESS.compareTo(bigInteger) >= 0
                && MIN_ADDRESS.compareTo(bigInteger) <= 0;
    }
}
