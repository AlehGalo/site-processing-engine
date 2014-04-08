/**
 * 
 */
package com.jdev.domain.domain.util;

import static com.jdev.domain.domain.util.InetAddressUtil.getNumberOfIpAddress;
import static com.jdev.domain.domain.util.InetAddressUtil.isValidAddress;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import com.jdev.domain.domain.util.InetAddressUtil;

/**
 * @author Aleh
 * 
 */
public class TestInetAddressUtil {

    @Test
    public void testIpv4_1() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("0.0.0.0").longValue(), 0L);
    }

    @Test
    public void testIpv4_2() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("231.13.55.46").longValue(),
                3876402990L);
    }

    @Test
    public void testIpv4_3() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("127.0.0.0").intValue(),
                2130706432L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpv4_4() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("256.0.0.0").intValue(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpv4_5() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("any.not-matching.string")
                .intValue(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpv4_6() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("").intValue(), 1);
    }

    @Test
    public void testIpv6_1() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("::1").longValue(), 1L);
    }

    @Test
    public void testIpv6_2() {
        Assert.assertEquals(
                InetAddressUtil.getNumberOfIpAddress("2001:db8:1234:0000:0000:0000:0000:0000"),
                new BigInteger("42540766416916187176308156905784606720"));
    }

    @Test
    public void testIpv6_3() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("::").intValue(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpv6_4() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("something is written:")
                .intValue(), 1);
    }

    @Test
    public void testIpv6_5() {
        Assert.assertEquals(InetAddressUtil.getNumberOfIpAddress("FE80:CD00::211E:729C"),
                new BigInteger("338292682821229838246455806028416971420"));
    }

    @Test
    public void testIpv6_6() {
        Assert.assertEquals(
                InetAddressUtil.getNumberOfIpAddress("FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF"),
                InetAddressUtil.MAX_ADDRESS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpv6_7() {
        Assert.assertEquals(
                InetAddressUtil.getNumberOfIpAddress("FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:10000")
                        .intValue(), new BigInteger("0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIpv6_8() {
        Assert.assertFalse(isValidAddress(getNumberOfIpAddress("-1")));
    }

    @Test
    public void testValidationOfAddress() {
        Assert.assertTrue(isValidAddress(getNumberOfIpAddress("::")));
    }

    @Test
    public void testValidationOfAddress1() {
        Assert.assertTrue(isValidAddress(getNumberOfIpAddress("255.255.255.255")));
    }

    @Test
    public void testValidationOfAddress2() {
        Assert.assertTrue(isValidAddress(getNumberOfIpAddress("FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF")));
    }

    @Test
    public void testValidationOfAddress3() {
        Assert.assertFalse(isValidAddress(new BigInteger("340282366920938463463374607431768211456")));
    }
}
