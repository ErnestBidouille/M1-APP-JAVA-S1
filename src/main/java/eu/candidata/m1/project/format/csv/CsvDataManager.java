package eu.candidata.m1.project.format.csv;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import eu.candidata.m1.project.format.json.JsonDataAnonymizer;
import eu.candidata.m1.project.format.json.JsonDataDescriptor;
import eu.candidata.m1.project.format.json.JsonDataValidator;

public class CsvDataManager implements Closeable, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvDataManager.class);
    private CSVParser csvParser;
    private JsonDataDescriptor jsonDataDescriptor;

    /**
     * Return CsvDataManager. The given csv is considered to have no first line
     * header. The columns will be in the order given by the JsonDataDescriptor.
     * 
     * @param reader             <code> not null </code>
     * @param jsonDataDescriptor <code> not null </code>
     * @return {@link CsvDataManager}CsvDataManager
     * @throws IOException
     */
    public static CsvDataManager getCsvReaderWithOutCsvHeaders(Reader reader, JsonDataDescriptor jsonDataDescriptor)
            throws IOException {
        Preconditions.checkNotNull(reader, "Reader can't be null");
        Preconditions.checkNotNull(jsonDataDescriptor, "JsonDataDescriptor can't be null");
        final String[] headers = jsonDataDescriptor.getHeaders().toArray(new String[0]);
        return new CsvDataManager(
                new CSVParser(reader,
                        CSVFormat.DEFAULT.withHeader(headers)),
                jsonDataDescriptor);
    }

    /**
     * Return CsvDataManager. The given csv is considered to have first line as
     * header.
     * 
     * @param reader             <code> not null </code>
     * @param jsonDataDescriptor <code>not null</code>
     * @return {@link CsvDataManager}
     * @throws IOException
     */
    public static CsvDataManager getCsvReaderWithCsvHeaders(Reader reader, JsonDataDescriptor jsonDataDescriptor)
            throws IOException {
        Preconditions.checkNotNull(reader, "Reader can't be null");
        Preconditions.checkNotNull(jsonDataDescriptor, "JsonDataDescriptor can't be null");
        return new CsvDataManager(new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()),
                jsonDataDescriptor);
    }

    private CsvDataManager(CSVParser csvParser, JsonDataDescriptor jsonDataDescriptor) {
        this.csvParser = csvParser;
        this.jsonDataDescriptor = jsonDataDescriptor;
    }

    /**
     * Validate data with given {@link JsonDataValidator} and write it to given file
     * path
     * 
     * @param jsonDataValidator {@link JsonDataValidator} <code>not null</code>
     * @param path              output String path <code>not null</code>
     * @throws IOException
     */
    public void validateAndWrite(JsonDataValidator jsonDataValidator, String path) throws IOException {
        Preconditions.checkNotNull(jsonDataValidator, "JsonDataValidator can't be null");
        Preconditions.checkNotNull(path, "path can't be null");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(path));
                CsvWritter csvWritter = new CsvWritter(bufferedWriter)) {
            csvParser.forEach(record -> {
                if (jsonDataDescriptor.validateRecordColumns(record)
                        && jsonDataValidator.validateRecordColumns(record)) {
                    try {
                        csvWritter.write(record);
                    } catch (IOException e) {
                        LOGGER.error("Can't write line", e);
                    }
                }
            });
            csvWritter.flush();
        }
    }

    /**
     * Anonymize data with given {@link JsonDataAnonymizer} and write it to given
     * file path
     * 
     * @param jsonDataAnonymizer {@link JsonDataAnonymizer} <code>not null</code>
     * @param path               output String path <code>not null</code>
     * @throws IOException
     */
    public void anonymizeAndWrite(JsonDataAnonymizer jsonDataAnonymizer, String path) throws IOException {
        Preconditions.checkNotNull(jsonDataAnonymizer, "JsonDataAnonymizer can't be null");
        Preconditions.checkNotNull(path, "path can't be null");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(path));
                CsvWritter csvWritter = new CsvWritter(bufferedWriter)) {
            csvParser.forEach(record -> {
                if (jsonDataDescriptor.validateRecordColumns(record)) {
                    try {
                        csvWritter.write(jsonDataAnonymizer.anonymizeRecordColumns(record));
                    } catch (IOException e) {
                        LOGGER.error("Can't write line", e);
                    }
                }
            });
            csvWritter.flush();
        }
    }

    /**
     * Validate data with given {@link JsonDataValidator} and anonymize it with
     * given {@link JsonDataAnonymizer}, finally write it to given file path
     * 
     * @param jsonDataAnonymizer      {@link JsonDataAnonymizer}
     *                                <code>not null</code>
     * @param jsonDataValidator{@link JsonDataValidator} <code>not null</code>
     * @param path                    output String path <code>not null</code>
     * @throws IOException
     */
    public void validateAnonymizeAndWrite(JsonDataValidator jsonDataValidator, JsonDataAnonymizer jsonDataAnonymizer,
            String path) throws IOException {
        Preconditions.checkNotNull(jsonDataValidator, "JsonDataValidator can't be null");
        Preconditions.checkNotNull(jsonDataAnonymizer, "JsonDataAnonymizer  can't be null");
        Preconditions.checkNotNull(path, "path can't be null");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(path));
                CsvWritter csvWritter = new CsvWritter(bufferedWriter)) {
            csvParser.forEach(record -> {
                if (jsonDataDescriptor.validateRecordColumns(record)
                        && jsonDataValidator.validateRecordColumns(record)) {
                    try {
                        csvWritter.write(jsonDataAnonymizer.anonymizeRecordColumns(record));
                    } catch (IOException e) {
                        LOGGER.error("Can't write line", e);
                    }
                }
            });
            csvWritter.flush();
        }
    }

    @Override
    public void close() {
        try {
            csvParser.close();
        } catch (Exception e) {}
    }

    public JsonDataDescriptor getJsonDataDescriptor() {
        return jsonDataDescriptor;
    }

    public CSVParser getCsvParser() {
        return csvParser;
    }
}
