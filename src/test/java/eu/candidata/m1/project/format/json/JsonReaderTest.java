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

    @Test
    void test() throws ParseException, IOException {
        File file = new File(String.valueOf(JsonReaderTest.class
                .getResource("ok.json").getFile()));
        JSONArray jsonReader = null;
        jsonReader = JsonReader.parseJsonFile(file);
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
        File file = new File(String.valueOf(JsonReaderTest.class.getResource("bad.json").getFile()));
        assertThrows(ParseException.class, () -> JsonReader.parseJsonFile(file));
    }
}
