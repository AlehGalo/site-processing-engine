/**
 * 
 */
package com.jdev.collector.job;

import org.springframework.util.Assert;

import com.jdev.crawler.core.user.ICompany;
import com.jdev.crawler.core.user.IUserData;
import com.jdev.crawler.core.user.UserData;
import com.jdev.domain.entity.Credential;

/**
 * @author Aleh
 * 
 */
public final class UserDataUtils {

    /**
     * 
     */
    private UserDataUtils() {
    }

    /**
     * Constant name.
     */
    private static final String NAME = "Name ";

    /**
     * @param credentials
     * @return
     */
    public static IUserData createUserData(final Credential credentials) {
        Assert.notNull(credentials);
        final Integer companyId = credentials.getSite().getId().intValue();
        return new UserData(credentials.getUsername(), credentials.getPassword(), new ICompany() {

            @Override
            public String getCompanyName() {
                return NAME + companyId;
            }

            @Override
            public Integer getCompanyId() {
                return companyId;
            }
        });
    }
}
