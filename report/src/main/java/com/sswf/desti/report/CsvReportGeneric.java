package com.sswf.desti.report;

import java.io.Closeable;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.Validate;

/**
 * @author Alexey Grigorev
 */
// TODO: merge with CsvReport!
public class CsvReportGeneric implements Closeable {

    private static final char QUOTE = '"';
    private static final char SEPARATOR = ';';

    private final PrintWriter csvWriter;

    public CsvReportGeneric(OutputStream csvStream) {
        Validate.notNull(csvStream);
        this.csvWriter = new PrintWriter(csvStream);
    }

    public void addLine(String... line) {
        addLine(Arrays.asList(line));
    }

    public void addLine(List<String> line) {
        Iterator<String> it = line.iterator();
        Validate.isTrue(it.hasNext());

        writeQuoted(it.next());

        while (it.hasNext()) {
            addSeparator();
            writeQuoted(it.next());
        }

        csvWriter.println();
        csvWriter.flush();
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
