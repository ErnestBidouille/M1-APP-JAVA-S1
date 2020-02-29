package eu.candidata.m1.project.format.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonReader.class);

    private JsonReader() {
    }

    /**
     * Return JSONArray from given file
     * 
     * @param file
     * @return JSONArray
     * @throws ParseException
     * @throws IOException
     */
    public static JSONArray parseJsonFile(File file) throws ParseException, IOException {
        try (FileReader fr = new FileReader(file)) {
            return parsedFile(fr);
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found");
            throw e;
        }
    }

    /**
     * Parse given FileReader
     * 
     * @param fr
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static JSONArray parsedFile(FileReader fr) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        try {
            return (JSONArray) jsonParser.parse(fr);
        } catch (ParseException e) {
            LOGGER.error("Parsing failed");
            throw e;
        }
    }
}
