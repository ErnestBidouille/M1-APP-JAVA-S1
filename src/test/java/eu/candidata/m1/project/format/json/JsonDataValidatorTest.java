package eu.candidata.m1.project.format.json;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eu.candidata.m1.project.format.csv.CsvDataManager;

class JsonDataValidatorTest {

    private static File okFile = new File(JsonDataDescriptorTest.class.getResource("validation/ok.json").getFile());
    private static File badFile = new File(JsonDataDescriptorTest.class.getResource("validation/bad.json").getFile());
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
    void test() throws ParseException, IOException {
        new JsonDataValidator(okFile);
    }

    @Test
    void testbadFile() throws ParseException, IOException {
        JsonDataValidator jsonDataValidator = new JsonDataValidator(badFile);
        Map<String, List<Object>> map = new LinkedHashMap<>();
        map.put("EMAIL_PRO", new LinkedList<>());
        assertEquals(map, jsonDataValidator.getMapAuthorizedRules());
    }

    @Test
    void testValidate() throws ParseException, IOException {
        JsonDataValidator jsonDataValidator = new JsonDataValidator(okFile);
        CSVRecord csvRecordFail = toTestCsvDataManager.getCsvParser().getRecords().get(2);
        assertFalse(jsonDataValidator.validateRecordColumns(csvRecordFail));
        for (CSVRecord csvRecord : toTestCsvDataManager.getCsvParser()) {
            assertTrue(jsonDataValidator.validateRecordColumns(csvRecord));
        }
    }
}
