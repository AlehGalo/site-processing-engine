/**
 * 
 */
package com.jdev.report;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.util.Assert;

import com.jdev.domain.Information;

/**
 * @author Aleh
 * 
 */
/**
 * @author Aleh
 * 
 */
public class SocialReport implements IGenerateReport<Information> {

    @Override
    public void writeReportIntoStream(final Information information, final OutputStream stream) {
        Assert.notNull(stream, "Output stream cannot be null.");
        Assert.notNull(information, "Spider cannot be null.");
        try {
            stream.write(StringUtils.getBytesUtf8("\n" + information.getSocial().toString()));
        } catch (IOException e) {
            // should not happen
            throw new RuntimeException(e.getMessage());
        }
    }

}
