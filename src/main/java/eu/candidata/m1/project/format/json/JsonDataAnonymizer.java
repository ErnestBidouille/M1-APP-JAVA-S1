package eu.candidata.m1.project.format.json;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.candidata.m1.project.rules.anonymization.Anonymizer;
import eu.candidata.m1.project.rules.anonymization.RandomLetterAnonymizer;
import eu.candidata.m1.project.rules.anonymization.RandomLocalPartLetterEmailAnonymizer;

public class JsonDataAnonymizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDataAnonymizer.class);
    private Map<String, AnonymizerRule> mapAnonymizerRules = new LinkedHashMap<>();

    private enum AnonymizerRule {

        RANDOM_LETTER(new RandomLetterAnonymizer()),
        RANDOM_LETTER_FOR_LOCAL_PART(new RandomLocalPartLetterEmailAnonymizer());

        private Anonymizer anonymizer;

        private AnonymizerRule(Anonymizer anonymizer) {
            this.anonymizer = anonymizer;
        }
    }

    @SuppressWarnings("unchecked")
    public JsonDataAnonymizer(File file) throws ParseException, IOException {
        JSONArray jsonArray = JsonReader.parseJsonFile(file);
        jsonArray.forEach(column -> checkValidFormat((JSONObject) column));
    }

    private void checkValidFormat(JSONObject column) {
        String nameVal;
        if ((nameVal = (String) column.get("name")) != null) {
            String changeTo;
            if ((changeTo = (String) column.get("changeTo")) != null) {
                try {
                    AnonymizerRule anonymizedRule = AnonymizerRule.valueOf(changeTo);
                    mapAnonymizerRules.put(nameVal, anonymizedRule);
                } catch (IllegalArgumentException e) {
                    LOGGER.warn("Illegal changeTo format ({}) for name {}, rule ignored", changeTo, nameVal);
                }
            } else {
                LOGGER.warn("changeTo field not found for name {}, rule ignored", nameVal);
            }
        } else {
            LOGGER.warn("name field not found, rule ignored");
        }
    }

    public List<String> anonRecordColumns(CSVRecord record) {
        List<String> anonymizedColumns = new LinkedList<>();
        mapAnonymizerRules.forEach((k, v) -> anonymizedColumns.add(v.anonymizer.apply(record.get(k))));
        return anonymizedColumns;
    }
}
