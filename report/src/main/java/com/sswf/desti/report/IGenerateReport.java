/**
 * 
 */
package com.sswf.desti.report;

import java.io.OutputStream;

/**
 * @author Aleh
 * 
 * @param <T>
 */
public interface IGenerateReport<T> {

    /**
     * @param t
     * @param stream
     */
    void writeReportIntoStream(final T t, final OutputStream stream);
}
