/**
 * 
 */
package com.jdev.domain.domain;

import static com.jdev.domain.InetAddressUtil.createInetAddress;
import static com.jdev.domain.InetAddressUtil.createIpv4Address;
import static com.jdev.domain.InetAddressUtil.createIpv6Address;
import static com.jdev.domain.domain.util.InetAddressUtil.MAX_ADDRESS;
import static com.jdev.domain.domain.util.InetAddressUtil.MIN_ADDRESS;

import java.math.BigInteger;

import org.junit.Test;

/**
 * @author Aleh
 * 
 */
public class TestInetAddressBean {

    @Test
    public void testCorrectIPv4Addresses() {
        createIpv4Address("0.0.0.0");
        createIpv4Address("255.255.255.255");
        createInetAddress(BigInteger.valueOf(0L));
        createInetAddress(BigInteger.valueOf(4294967295L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectIPv4Address() {
        createIpv4Address("256.0.0.0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectIPv4Address1() {
        createIpv4Address("any.string.to.be");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectIPv4Address2() {
        createIpv4Address("1.0.256.0");
    }

    @Test
    public void testIncorrectIPv4Address3() {
        createInetAddress(BigInteger.valueOf(4294967296L));
    }

    @Test
    public void testCorrectAddresses() {
        createIpv6Address("::");
        createIpv6Address("FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF");
        createIpv6Address("FE80:CD00::211E:729C");
        createIpv6Address("::1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectIPv6Address() {
        createIpv6Address("FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:10000");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectAddress1() {
        createIpv6Address("any::string:to:be");
    }

    @Test
    public void testCorrectAddress3() {
        createInetAddress(MIN_ADDRESS);
    }

    @Test
    public void testCorrectAddress4() {
        createInetAddress(MAX_ADDRESS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectAddress2() {
        createInetAddress(MAX_ADDRESS.add(BigInteger.valueOf(1L)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectAddress3() {
        createInetAddress(MIN_ADDRESS.subtract(BigInteger.valueOf(1L)));
    }

}
