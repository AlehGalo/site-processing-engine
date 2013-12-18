/**
 * 
 */
package com.jdev.report;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.util.Assert;

import com.jdev.domain.Information;
import com.jdev.domain.mail.IMail;

/**
 * @author Aleh
 * 
 */
public class InformationReport implements IGenerateReport<Information> {

    @Override
    public void writeReportIntoStream(final Information i, final OutputStream stream) {
        Assert.notNull(stream, "Output stream cannot be null.");
        Assert.notNull(i, "Information cannot be null.");
        try {
            final Set<IMail> set = i.getMails();
            stream.write(StringUtils
                    .getBytesUtf8("\n------------------------ExtractionResult(beta)---------------------------"));
            stream.write(StringUtils.getBytesUtf8("\nEmails " + set.size()));
            if (!set.isEmpty()) {
                stream.write(StringUtils.getBytesUtf8(": "));
            }
            for (final IMail iMail : set) {
                stream.write(StringUtils.getBytesUtf8(iMail.getMail() + " \n"));
            }
            stream.write("\n".getBytes());
            stream.write(StringUtils.getBytesUtf8("\nPhones " + i.getPhoneNumbers().size()));
            if (!i.getPhoneNumbers().isEmpty()) {
                stream.write(StringUtils.getBytesUtf8(": "));
            }
            for (final String phone : i.getPhoneNumbers()) {
                stream.write(StringUtils.getBytesUtf8(phone + " \n"));
            }
            stream.write("\n".getBytes());
            stream.write(StringUtils.getBytesUtf8("\nPrices " + i.getSetOfPricesTemp().size()));
            if (!i.getSetOfPricesTemp().isEmpty()) {
                stream.write(StringUtils.getBytesUtf8(": "));
            }
            for (final String price : i.getSetOfPricesTemp()) {
                stream.write(StringUtils.getBytesUtf8("\n" + price));
            }
        } catch (IOException e) {
            // should not happen
            throw new RuntimeException(e.getMessage());
        }

    }

}
