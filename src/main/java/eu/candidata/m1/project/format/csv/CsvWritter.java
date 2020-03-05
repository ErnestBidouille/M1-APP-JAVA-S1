package eu.candidata.m1.project.format.csv;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvWritter implements Flushable, Closeable, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvWritter.class);
    private CSVPrinter csvPrinter;

    public CsvWritter(BufferedWriter bufferedWriter) throws IOException {
        try {
            csvPrinter = new CSVPrinter(bufferedWriter, CSVFormat.DEFAULT);
        } catch (IOException e) {
            LOGGER.error("CsvWritter error");
            throw e;
        }
    }

    /**
     * 
     * Print a List of Strings in csvPrinter
     * 
     * @param csvRecord
     * @throws IOException If an I/O error occurs
     */
    public void write(List<String> csvRecord) throws IOException {
        csvPrinter.printRecord(csvRecord);
    }

    /**
     * Print CSVRecord in csvPrinter
     * 
     * @param csvRecord
     * @throws IOException If an I/O error occurs
     */
    public void write(CSVRecord csvRecord) throws IOException {
        csvPrinter.printRecord(csvRecord);
    }

    @Override
    public void flush() {
        try {
            csvPrinter.flush();
        } catch (IOException e) {
            LOGGER.error("Flushing failed");
        }
    }

    @Override
    public void close() {
        try {
            csvPrinter.close();
        } catch (IOException e) {}
    }
}
