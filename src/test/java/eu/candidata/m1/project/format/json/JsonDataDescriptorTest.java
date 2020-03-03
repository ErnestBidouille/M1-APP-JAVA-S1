package eu.candidata.m1.project.format.json;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

import org.apache.commons.csv.CSVRecord;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eu.candidata.m1.project.format.csv.CsvDataManager;

public class JsonDataDescriptorTest {

    private static File okFile = new File(JsonDataDescriptorTest.class.getResource("description/ok.json").getFile());
    private static File badFile = new File(
            JsonDataDescriptorTest.class.getResource("description/bad.json").getFile());
    private static CsvDataManager toTestCsvDataManager;

    @BeforeAll
    static void createFilesCsv() throws ParseException, IOException {
        final Reader reader = new FileReader(new File(CsvDataManager.class.getResource("data.csv").getFile()));
        final File descriptionFile = new File(
                JsonDataDescriptorTest.class.getResource("description/ok.json").getFile());
        toTestCsvDataManager = CsvDataManager.getCsvReaderWithCsvHeaders(reader,
                new JsonDataDescriptor(descriptionFile));
    }

    @Test
    void testHeader() throws ParseException, IOException {
        JsonDataDescriptor descriptor = new JsonDataDescriptor(okFile);
        String[] expectedHeader = new String[] { "NOM", "AGE", "DATE_DE_NAISSANCE", "EMAIL_PRO", "EMAIL_PERSO" };
        assertArrayEquals(expectedHeader, descriptor.getHeaders().toArray(new String[0]));
    }

    @Test
    void testBadFile() throws ParseException, IOException {
        JsonDataDescriptor jsonDataDescriptor = new JsonDataDescriptor(badFile);
        assertEquals(new LinkedList<String>(), jsonDataDescriptor.getHeaders());
    }

    @Test
    void testValidate() throws ParseException, IOException {
        JsonDataDescriptor jsonDataDescriptor = new JsonDataDescriptor(okFile);
        CSVRecord csvRecordFail = toTestCsvDataManager.getCsvParser().getRecords().get(2);
        assertFalse(jsonDataDescriptor.validateRecordColumns(csvRecordFail));
        for (CSVRecord csvRecord : toTestCsvDataManager.getCsvParser()) {
            assertTrue(jsonDataDescriptor.validateRecordColumns(csvRecord));
        }
    }
}
