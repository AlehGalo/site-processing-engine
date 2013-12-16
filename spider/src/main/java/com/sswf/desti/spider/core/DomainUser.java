/**
 * 
 */
package com.sswf.desti.spider.core;

import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.UserData;

/**
 * @author Aleh
 * 
 */
final class DomainUser extends UserData {

    /**
     * 
     */
    private static final String PASSWORD = "PASSWORD";

    /**
     * 
     */
    private static final String LOGIN = "CRAWLER";

    /**
     * 
     */
    private static final String PHONE_NUMBER = "main";

    /**
     * 
     */
    private static final String SECURITY_CODE = "code";

    /**
     * 
     */
    private static final String COMPANY_NAME = "SSWF";

    /**
     * 
     */
    private static final ICompany COMPANY;

    /**
     * Domain user.
     */
    private static DomainUser DOMAIN_USER;

    static {
        COMPANY = new ICompany() {

            @Override
            public String getCompanyName() {
                return COMPANY_NAME;
            }

            @Override
            public Integer getCompanyId() {
                return 1;
            }
        };
    }

    /**
     * Default constructor.
     */
    private DomainUser() {
        super(LOGIN, PASSWORD, PHONE_NUMBER, SECURITY_CODE, COMPANY);
    }

    /**
     * @return instance of DomainUser.
     */
    public final static UserData getInstance() {
        if (DOMAIN_USER == null) {
            DOMAIN_USER = new DomainUser();
        }
        return DOMAIN_USER;
    }
}
