/**
 * 
 */
package com.jdev.domain.dao;

import static com.jdev.domain.InetAddressUtil.createIpv4Address;
import static com.jdev.domain.InetAddressUtil.createIpv6Address;

import com.jdev.domain.domain.InetAddress;

/**
 * @author Aleh
 * 
 */
public class InetAddressDaoTest extends AbstractWriteDaoTest<InetAddress> {

    @Override
    InetAddress createEntity() {
        return createIpv4Address("255.0.0.0");
    }

    @Override
    InetAddress createUpdateEntity() {
        return createIpv6Address("::235");
    }

}
