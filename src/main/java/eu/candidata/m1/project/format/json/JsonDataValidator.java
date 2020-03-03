package eu.candidata.m1.project.format.json;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import eu.candidata.m1.project.rules.validation.Rule;
import eu.candidata.m1.project.rules.validation.ValidationRules;

public class JsonDataValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDataValidator.class);
    private Map<String, List<AuthorizedRules>> mapAuthorizedRules = new LinkedHashMap<>();

    public enum AuthorizedRules {
        BE_AN_AGE(ValidationRules.IS_AGE),
        BE_AN_EMAIL(ValidationRules.IS_EMAIL),
        BE_AN_DAUPHINE_EMAIL(ValidationRules.IS_DAUPHINE_EMAIL);

        private Rule rule;

        private AuthorizedRules(Rule rule) {
            this.rule = rule;
        }
    }

    public JsonDataValidator(File file) throws ParseException, IOException {
        Preconditions.checkNotNull(file, "file can't be null");
        JSONArray jsonArray = JsonReader.parseJsonFile(file);
        for (Object rule : jsonArray) {
            try {
                checkValidFormat((JSONObject) rule);
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    /**
     * Map all 'should' rules to the relevant columns
     * 
     * @param column
     */
    private void putShouldRules(JSONArray shouldStrings, String nameVal) {
        List<AuthorizedRules> tmpAuthorizedRules = new LinkedList<>();
        for (Object shouldString : shouldStrings) {
            try {
                AuthorizedRules authorizedRule = AuthorizedRules.valueOf((String) shouldString);
                tmpAuthorizedRules.add(authorizedRule);
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Illegal should format ({}) for name {}, rule ignored", shouldString, nameVal);
            }
        }
        mapAuthorizedRules.put(nameVal, tmpAuthorizedRules);
    }

    /**
     * Check if column if in valid format and add id to the columns/rule Map. Value
     * is ignored if should isn't in right format or object havn't "name" or
     * "should" fields
     * 
     * @param column <code>not null</code>
     * 
     */
    private void checkValidFormat(JSONObject column) {
        Preconditions.checkNotNull(column, "Column can't be null");
        String nameVal;
        if ((nameVal = (String) column.get("name")) != null) {
            JSONArray shouldStrings;
            try {
                if ((shouldStrings = (JSONArray) column.get("should")) != null) {
                    putShouldRules(shouldStrings, nameVal);
                } else {
                    throw new IllegalArgumentException(
                            String.format("should field not found for name %s, rule ignored", nameVal));
                }
            } catch (ClassCastException e) {
                throw new IllegalArgumentException(
                        String.format("Illegal change should format for name %s, must be an array", nameVal), e);
            }
        } else {
            throw new IllegalArgumentException("name field not found, rule ignored");
        }
    }

    /**
     * Validate given CSVRecord
     * 
     * @param record to valid
     * @return <code>true</code> if column is valid <code>false</code> otherwise
     */
    public boolean validateRecordColumns(CSVRecord record) {
        for (Entry<String, List<AuthorizedRules>> entry : mapAuthorizedRules.entrySet()) {
            for (AuthorizedRules authorizedRule : entry.getValue()) {
                try {
                    if (!authorizedRule.rule.test(record.get(entry.getKey()))) {
                        return false;
                    }
                } catch (IllegalArgumentException e) {}
            }
        }
        return true;
    }

    public Map<String, List<AuthorizedRules>> getMapAuthorizedRules() {
        return mapAuthorizedRules;
    }
}
