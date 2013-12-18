/**
 * 
 */
package com.jdev.report;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.jdev.domain.Information;

/**
 * @author Aleh
 * 
 */
public class XlsInformationReport implements IGenerateReport<Information> {

    /**
     * 
     */
    private final Workbook wb = new XSSFWorkbook();

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XlsInformationReport.class);

    @Override
    public void writeReportIntoStream(final Information t, final OutputStream stream) {
        Assert.notNull(stream, "Output stream cannot be null.");
        Assert.notNull(t, "Information cannot be null.");
        // Sheet sheet = createHeader(t);
        // int rowNumber = 1;
        // for (String priceWithServiceString : t.getSetOfPricesTemp()) {
        // try {
        // TODO: replace with general code.
        // fillRowWithData(sheet.createRow(rowNumber), new
        // MessageFormat(
        // PriceExtractor.PRICE_PATTERN).parse(priceWithServiceString));
        // ++rowNumber;
        // } catch (ParseException e) {
        // LOGGER.error(e.getMessage());
        // }
        // }
        try {
            wb.write(stream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
    //
    // /**
    // * @param source
    // * @param rowData
    // */
    // private void fillRowWithData(final Row source, final Object... rowData) {
    // if (rowData != null && source != null) {
    // for (int i = 0; i < rowData.length; i++) {
    // source.createCell(i).setCellValue(rowData[i].toString());
    // }
    // }
    // }
    //
    // /**
    // * @param inf
    // * @return sheet.
    // */
    // private Sheet createHeader(final Information inf) {
    // Sheet sheet = wb.createSheet(inf.getHomeUrl().getHost());
    // Row row = sheet.createRow(0);
    // fillRowWithData(row, new Object[] { "Price", "Service" });
    // return sheet;
    // }

}
