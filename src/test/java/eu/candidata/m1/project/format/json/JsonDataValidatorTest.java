package eu.candidata.m1.project.format.json;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class JsonDataValidatorTest {

    private static File okFile = new File(JsonCsvDescriptorTest.class.getResource("validation/ok.json").getFile());
    private static File badFile = new File(JsonCsvDescriptorTest.class.getResource("validation/bad.json").getFile());

    @Test
    void test() throws ParseException, IOException {
        new JsonDataValidator(okFile);
    }

    @Test
    void testbadFile() throws ParseException, IOException {
        new JsonDataValidator(badFile);
    }
}
