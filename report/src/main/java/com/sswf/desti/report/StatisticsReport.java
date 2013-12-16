/**
 * 
 */
package com.sswf.desti.report;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.util.Assert;

import com.sswf.desti.domain.statistics.IStatistics;

/**
 * @author Aleh
 * 
 */
public class StatisticsReport implements IGenerateReport<IStatistics> {

    @Override
    public void writeReportIntoStream(final IStatistics s, final OutputStream os) {
        Assert.notNull(os, "Output stream cannot be null.");
        Assert.notNull(s, "Statistics cannot be null.");
        try {
            os.write(StringUtils
                    .getBytesUtf8("\n=====================Statistics(beta)==========================="));
            os.write(StringUtils.getBytesUtf8("\nProcessed: " + s.getTotalLinksCount() + " links"));
            os.write(StringUtils.getBytesUtf8("\nBytes transfered: " + s.getDataTransfered()));
            os.write(StringUtils.getBytesUtf8("\nSpend time: " + s.getTimeSpent()));
            os.write(StringUtils.getBytesUtf8("\nInvalid urls: " + s.getInvalidUrlLinks().size()));
        } catch (IOException e) {
            // should not happen
            throw new RuntimeException(e.getMessage());
        }
    }

}
