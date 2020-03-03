package eu.candidata.m1.project.format.csv;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eu.candidata.m1.project.format.json.JsonDataDescriptor;

class CsvDataManagerTest {

    private static JsonDataDescriptor jsonDataDescriptor;
    private static Reader reader;

    @BeforeAll
    private static void createFiles() throws ParseException, IOException {
        jsonDataDescriptor = new JsonDataDescriptor(
                new File(JsonDataDescriptor.class.getResource("description/ok.json").getFile()));
        reader = new FileReader(new File(CsvDataManagerTest.class.getResource("data.csv").getFile()));
    }

    @Test
    void testConstructorWithHeader() throws IOException {
        CsvDataManager csvDataManager = CsvDataManager.getCsvReaderWithCsvHeaders(reader, jsonDataDescriptor);
        assertEquals(csvDataManager.getCsvParser().getHeaderNames(),
                jsonDataDescriptor.getHeaders());
        assertEquals(csvDataManager.getJsonDataDescriptor(), jsonDataDescriptor);
    }

    @Test
    void testConstructorWithoutHeader() throws IOException {
        CsvDataManager csvDataManager = CsvDataManager.getCsvReaderWithOutCsvHeaders(reader, jsonDataDescriptor);
        assertEquals(csvDataManager.getCsvParser().getHeaderNames(),
                jsonDataDescriptor.getHeaders());
        assertEquals(csvDataManager.getJsonDataDescriptor(), jsonDataDescriptor);
    }

    @Test
    void testClose() throws IOException {
        CsvDataManager csvDataManager = CsvDataManager.getCsvReaderWithCsvHeaders(
                new FileReader(new File(CsvDataManagerTest.class.getResource("data.csv").getFile())),
                jsonDataDescriptor);
        assertFalse(csvDataManager.getCsvParser().isClosed());
        csvDataManager.close();
        assertTrue(csvDataManager.getCsvParser().isClosed());
    }
}
