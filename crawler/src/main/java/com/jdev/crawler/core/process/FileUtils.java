package com.jdev.crawler.core.process;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jdev.crawler.core.user.IUserData;

/**
 * @author Aleh
 * 
 */
public final class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
    }

    /**
     * @param context
     * @return
     */
    public static File getJobPath(final IProcessContext context) {
        return getJobPath(context.getUserData());
    }

    /**
     * @param userData
     * @return
     */
    public static File getJobPath(final IUserData userData) {
        final File companyRoot = new File(System.getProperty("java.io.tmpdir"), ""
                + userData.getCompany().getCompanyId());
        final File f = new File(companyRoot,
                isNotEmpty(userData.getUniqueKey()) ? userData.getUniqueKey() : userData.getLogin());
        final File file = new File(f, userData.getUUID());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(file.getAbsolutePath());
        }
        return file;
    }

    /**
     * @param desc
     * @return
     */
    public static String getFileName(final IDescription desc) {
        return System.currentTimeMillis() + "_"
                + (desc.getDescription() == null ? StringUtils.EMPTY : desc.getDescription())
                + ".html";
    }

}
