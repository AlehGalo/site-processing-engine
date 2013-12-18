/**
 * 
 */
package com.sswf.desti.extractor.common;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.jdev.domain.price.Price;

/**
 * @author Aleh
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class PriceAccumulationBean {

    /**
	 * 
	 */
    @XmlElementWrapper(name = "prices")
    @XmlElement(name = "atomic")
    private Set<Price> prices;

    /**
     * @return the setOfPrices
     */
    public Set<Price> getPrices() {
        return prices;
    }

    /**
     * @param setOfPrices
     *            the setOfPrices to set
     */
    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }
}
