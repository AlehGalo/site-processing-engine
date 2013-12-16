package com.sswf.desti.report;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.sswf.desti.domain.Information;
import com.sswf.desti.domain.TargetWebSite;
import com.sswf.desti.domain.mail.IMail;

/**
 * @author Alexey Grigorev
 */
public class CsvReport implements Closeable {

    private static final int THRESHOLD = 5;

    private static final char QUOTE = '"';
    private static final char SEPARATOR = ';';

    private final PrintWriter csvWriter;

    public CsvReport(OutputStream csvStream) {
        Validate.notNull(csvStream);
        this.csvWriter = new PrintWriter(csvStream);
    }

    public CsvReport(Writer csvStream) {
        Validate.notNull(csvStream);
        this.csvWriter = new PrintWriter(csvStream);
    }

    
    public void writeHeaders() {

    }

    public void addLine(TargetWebSite website, Information info) {
        Validate.notNull(website);
        Validate.notNull(info);

        writeQuoted(website.getId());
        addSeparator();

        writeQuoted(website.getPoi());
        addSeparator();

        writeQuoted(website.getUrl());
        addSeparator();

        Set<String> phoneNumbers = info.getPhoneNumbers();
        if (phoneNumbers.size() > THRESHOLD) {
            writeQuoted("many - " + phoneNumbers.size());
        } else {
            writeQuoted(StringUtils.join(phoneNumbers, ','));
        }
        addSeparator();

        Set<IMail> emails = info.getMails();
        if (emails.size() > THRESHOLD) {
            writeQuoted("many - " + emails.size());
        } else {
            List<String> stringEmails = extractEmails(emails);
            writeQuoted(StringUtils.join(stringEmails, ','));
        }
        addSeparator();

        String allLinksAsCsv = info.getSocial().allLinksAsCsv();
        writeQuoted(allLinksAsCsv);

        csvWriter.println();
        csvWriter.flush();
    }

    private static List<String> extractEmails(Set<IMail> emails) {
        List<String> res = new ArrayList<>(emails.size());
        for (IMail mail : emails) {
            res.add(mail.getMail());
        }
        return res;
    }

    private void addSeparator() {
        csvWriter.print(SEPARATOR);
    }

    private void writeQuoted(String path) {
        csvWriter.print(quoted(path));
    }

    private static String quoted(String string) {
        return QUOTE + string + QUOTE;
    }

    @Override
    public void close() {
        csvWriter.close();
    }

}
