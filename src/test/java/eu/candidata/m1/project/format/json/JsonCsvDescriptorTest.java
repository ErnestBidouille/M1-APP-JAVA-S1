package eu.candidata.m1.project.format.json;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class JsonCsvDescriptorTest {

    private static File okFile = new File(JsonCsvDescriptorTest.class.getResource("description/ok.json").getFile());
    private static File badFile = new File(
            JsonCsvDescriptorTest.class.getResource("description/bad.json").getFile());

    @Test
    void testHeader() throws ParseException, IOException {
        JsonCsvDescriptor descriptor = new JsonCsvDescriptor(okFile);
        String[] expectedHeader = new String[] { "NOM", "AGE", "DATE_DE_NAISSANCE", "EMAIL_PRO", "EMAIL_PERSO" };
        assertArrayEquals(expectedHeader, descriptor.getHeaders().toArray(new String[0]));
    }

    @Test
    void testBadFile() throws ParseException, IOException {
        new JsonCsvDescriptor(badFile);
    }
}
