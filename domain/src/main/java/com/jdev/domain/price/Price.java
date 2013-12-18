/**
 * 
 */
package com.jdev.domain.price;

/**
 * @author Aleh
 * 
 */
public class Price implements IPrice {

	/**
     * 
     */
	private String price;

	/**
     * 
     */
	private String service;

	/**
     * 
     */
	public Price() {
	}

	/**
	 * @param price
	 * @param service
	 */
	public Price(String price, String service) {
		this.price = price;
		this.service = service;
	}

	/**
	 * @return the service
	 */
	@Override
	public String getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(final String service) {
		this.service = service;
	}

	/**
	 * @return the price
	 */
	@Override
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(final String price) {
		this.price = price;
	}

}
