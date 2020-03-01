package eu.candidata.m1.project.format.json;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.candidata.m1.project.rules.validation.Rule;
import eu.candidata.m1.project.rules.validation.ValidationRules;

public class JsonCsvDescriptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonCsvDescriptor.class);
    private List<Pair<String, DataType>> listColumns = new LinkedList<>();

    private enum DataType {
        STRING(ValidationRules.IS_STRING),
        INT(ValidationRules.IS_INTEGER),
        DOUBLE(ValidationRules.IS_DOUBLE);

        private Rule rule;

        private DataType(Rule rule) {
            this.rule = rule;
        }
    }

    public JsonCsvDescriptor(File file) throws ParseException, IOException {
        JSONArray jsonArray = JsonReader.parseJsonFile(file);
        for (Object column : jsonArray) {
            try {
                checkValidFormat((JSONObject) column);
            } catch (Exception e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    /**
     * Check if column is in valid format and add id to the columns Map. Value is
     * ignored if dataType isn't in right format or object havn't "name" or
     * "dataType" fields
     * 
     * @param column
     */
    private void checkValidFormat(JSONObject column) throws IllegalArgumentException {
        String nameVal;
        if ((nameVal = (String) column.get("name")) != null) {
            String dataType = (String) column.get("dataType");
            if (dataType != null) {
                try {
                    DataType dataTypeColumn = DataType.valueOf(dataType);
                    listColumns.add(Pair.of(nameVal, dataTypeColumn));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(String.format(
                            "dataType field bad format (%s) for name %s, description ignored", dataType, nameVal), e);
                }
            } else {
                throw new IllegalArgumentException(
                        String.format("dataType field not found for name %s, description ignored", nameVal));
            }
        } else {
            throw new IllegalArgumentException("name field not found, description ignored");
        }
    }

    public boolean validateRecordColumns(CSVRecord record) {
        for (Pair<String, DataType> pair : listColumns) {
            try {
                if (!pair.getValue().rule.test(record.get(pair.getKey()))) {
                    return false;
                }
            } catch (IllegalArgumentException e) {}
        }
        return true;
    }

    public List<String> getHeaders() {
        List<String> tmpListStrings = new LinkedList<>();
        for (Pair<String, DataType> pair : listColumns) {
            tmpListStrings.add(pair.getKey());
        }
        return tmpListStrings;
    }
}
