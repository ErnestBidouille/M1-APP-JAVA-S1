package eu.candidata.m1.project.format.json;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class JsonDataAnonymizerTest {

    private static File okFile = new File(JsonCsvDescriptorTest.class.getResource("anonymizer/ok.json").getFile());
    private static File badFile = new File(JsonCsvDescriptorTest.class.getResource("anonymizer/bad.json").getFile());

    @Test
    void test() throws ParseException, IOException {
        new JsonDataAnonymizer(okFile);
    }

    @Test
    void testBadFile() throws ParseException, IOException {
        new JsonDataAnonymizer(badFile);
    }
}
