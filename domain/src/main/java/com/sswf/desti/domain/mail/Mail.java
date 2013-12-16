/**
 * 
 */
package com.sswf.desti.domain.mail;

/**
 * @author Aleh
 * 
 */
public class Mail implements IMail {

    /**
     * 
     */
    private String email;

    /**
     * @param mail
     */
    public Mail(final String mail) {
	this.email = mail;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.sswf.desti.domain.mail.IMail#getMail()
     */
    @Override
    public String getMail() {
	return email;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return email == null ? 0 : email.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
	return email == null ? false : email.equals(((IMail) obj).getMail());
    }
}
