package eu.candidata.m1.project.format.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class JsonReaderTest {

    private static File okFile = new File(JsonReaderTest.class.getResource("ok.json").getFile());
    private static File badFile = new File(JsonReaderTest.class.getResource("bad.json").getFile());

    @Test
    void test() throws ParseException, IOException {
        JSONArray jsonReader = null;
        jsonReader = JsonReader.parseJsonFile(okFile);
        assertEquals(2, jsonReader.toArray().length);
        assertEquals(
                "[{\"dataType\":\"datatypeTest\",\"name\":\"Test\",\"should\":\"ShouldTest\",\"changeTo\":\"changeToTest\"},{\"dataType\":\"datatypeTest2\",\"name\":\"Test2\",\"should\":\"ShouldTest2\",\"changeTo\":\"changeToTest2\"}]",
                jsonReader.toString());
    }

    @Test
    void testFileNotFoundException() {
        File file = new File("");
        assertThrows(FileNotFoundException.class, () -> JsonReader.parseJsonFile(file));
    }

    @Test
    void testParsingFailException() {
        assertThrows(ParseException.class, () -> JsonReader.parseJsonFile(badFile));
    }
}
