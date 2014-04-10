///**
// * 
// */
//package com.jdev.domain.domain;
//
//import static com.jdev.domain.domain.util.InetAddressUtil.getNumberOfIpAddress;
//import static com.jdev.domain.domain.util.InetAddressUtil.isValidAddress;
//import static java.text.MessageFormat.format;
//
//import java.math.BigInteger;
//
//import javax.persistence.AttributeOverride;
//import javax.persistence.AttributeOverrides;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
///**
// * @author Aleh
// * 
// */
//@Entity
//@Table(name = "NETWORK_ADDRESS")
//@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "IP_ADDRESS_ID")) })
//public class InetAddress extends AbstractIdentifiable {
//
//    /**
//     * Default serial version id.
//     */
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 
//     */
//    @Column(name = "IP_ADDRESS", nullable = false)
//    private String ipAddress;
//
//    /**
//     * Messaging of errors.
//     */
//    private static final String ERROR_MESSAGE_TEMPLATE = "{0} is not valid ipv4 or ipv6 net address";
//
//    /**
//     * Default constructor.
//     */
//    public InetAddress() {
//    }
//
//    /**
//     * @return the ipAddress
//     */
//    public BigInteger getIpAddress() {
//        return new BigInteger(this.ipAddress);
//    }
//
//    /**
//     * @param ipAddress
//     *            the ipAddress to set
//     */
//    public void setIpAddress(final BigInteger ipAddress) {
//        if (isValidAddress(ipAddress)) {
//            this.ipAddress = ipAddress.toString();
//        } else {
//            throw new IllegalArgumentException(format(ERROR_MESSAGE_TEMPLATE, ipAddress));
//        }
//    }
//
//    /**
//     * @param ipAddress
//     *            both ipv4 and ipv6 address. 1.12.25.0, ::1
//     */
//    public void setCanonicalIpAddress(final String ipAddress) {
//        setIpAddress(getNumberOfIpAddress(ipAddress));
//    }
//
//    /**
//     * @param ipAddress
//     *            number is string presentation.
//     */
//    void setIpAddress(final String ipAddress) {
//        this.ipAddress = ipAddress;
//    }
// }
