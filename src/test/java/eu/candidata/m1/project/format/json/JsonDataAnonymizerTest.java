package eu.candidata.m1.project.format.json;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eu.candidata.m1.project.format.csv.CsvDataManager;
import eu.candidata.m1.project.format.json.JsonDataAnonymizer.AnonymizerRule;

class JsonDataAnonymizerTest {

    private static File okFile = new File(JsonDataDescriptorTest.class.getResource("anonymizer/ok.json").getFile());
    private static File badFile = new File(JsonDataDescriptorTest.class.getResource("anonymizer/bad.json").getFile());
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
        JsonDataAnonymizer jsonDataAnonymizer = new JsonDataAnonymizer(okFile);
        jsonDataAnonymizer.getMapAnonymizerRules();
        Map<String, AnonymizerRule> mapAnonymizerRules = new LinkedHashMap<>();
        mapAnonymizerRules.put("NOM", AnonymizerRule.RANDOM_LETTER);
        mapAnonymizerRules.put("EMAIL_PERSO", AnonymizerRule.RANDOM_LETTER_FOR_LOCAL_PART);
        assertEquals(mapAnonymizerRules, jsonDataAnonymizer.getMapAnonymizerRules());
    }

    @Test
    void testBadFile() throws ParseException, IOException {
        JsonDataAnonymizer jsonDataAnonymizer = new JsonDataAnonymizer(badFile);
        Map<String, AnonymizerRule> mapAnonymizerRules = new LinkedHashMap<>();
        assertEquals(mapAnonymizerRules, jsonDataAnonymizer.getMapAnonymizerRules());
    }

    @Test
    void testAnon() throws ParseException, IOException {
        JsonDataAnonymizer jsonDataAnonymizer = new JsonDataAnonymizer(okFile);
        for (CSVRecord csvRecord : toTestCsvDataManager.getCsvParser()) {
            List<String> tooTest = jsonDataAnonymizer.anonRecordColumns(csvRecord);
            assertEquals(2, tooTest.size());
            assertEquals(3, tooTest.get(0).length());
            assertNotEquals(csvRecord.get("EMAIL_PRO"), tooTest.get(1));
            assertTrue(tooTest.get(1).contains("@gmail.com"));
        }
    }
}
