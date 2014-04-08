/**
 * 
 */
package com.jdev.domain;

import java.math.BigInteger;

import com.jdev.domain.domain.InetAddress;

/**
 * @author Aleh
 * 
 */
public final class InetAddressUtil {

    /**
     * @param ipv4
     *            string presentation.
     * @return IPv4 address.
     */
    public static final InetAddress createIpv4Address(final String ipv4) {
        InetAddress ipv4address = new InetAddress();
        ipv4address.setCanonicalIpAddress(ipv4);
        return ipv4address;
    }

    /**
     * @param inetAddress
     *            string presentation.
     * @return inet address.
     */
    public static final InetAddress createInetAddress(final BigInteger inetAddressNumber) {
        InetAddress inetAddress = new InetAddress();
        inetAddress.setIpAddress(inetAddressNumber);
        return inetAddress;
    }

    /**
     * @param ipv6
     *            string presentation.
     * @return IPv6 address.
     */
    public static final InetAddress createIpv6Address(final String ipv6) {
        InetAddress ipv4address = new InetAddress();
        ipv4address.setIpAddress(com.jdev.domain.domain.util.InetAddressUtil
                .getNumberOfIpAddress(ipv6));
        return ipv4address;
    }
}
