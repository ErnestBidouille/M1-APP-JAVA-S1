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

import com.google.common.base.Preconditions;

import eu.candidata.m1.project.rules.validation.Rule;
import eu.candidata.m1.project.rules.validation.ValidationRules;

public class JsonDataDescriptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDataDescriptor.class);
    private List<Pair<String, DataType>> listColumns = new LinkedList<>();

    public enum DataType {
        STRING(ValidationRules.IS_STRING),
        INT(ValidationRules.IS_INTEGER),
        DOUBLE(ValidationRules.IS_DOUBLE);

        private Rule rule;

        private DataType(Rule rule) {
            this.rule = rule;
        }
    }

    @SuppressWarnings("unchecked")
    public JsonDataDescriptor(File file) throws ParseException, IOException {
        Preconditions.checkNotNull(file, "File can't be null");
        JSONArray jsonArray = JsonReader.parseJsonFile(file);
        jsonArray.forEach(column -> addDescriptionRuleToGivenColumn((JSONObject) column));
    }

    /**
     * Adds an descriptor rule to the given column. Check if column is in valid
     * format and add id to the columns/rule Pair list. Value is ignored if dataType
     * isn't in right format or object havn't "name" or "dataType" fields
     * 
     * @param column <code>not null</code>
     */
    private void addDescriptionRuleToGivenColumn(JSONObject column) {
        Preconditions.checkNotNull(column, "Column can't be null");
        String nameVal;
        if ((nameVal = (String) column.get("name")) != null) {
            String dataType = (String) column.get("dataType");
            if (dataType != null) {
                try {
                    DataType dataTypeColumn = DataType.valueOf(dataType);
                    listColumns.add(Pair.of(nameVal, dataTypeColumn));
                } catch (IllegalArgumentException e) {
                    LOGGER.warn("dataType field bad format ({}) for name {}, description ignored", dataType, nameVal);
                }
            } else {
                LOGGER.warn("dataType field not found for name {}, description ignored", nameVal);
            }
        } else {
            LOGGER.warn("name field not found, description ignored");
        }
    }

    /**
     * Validate given CSVRecord
     * 
     * @param record to validate
     * @return <code>true</code> if column is valid <code>false</code> otherwise
     */
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

    /**
     * Return all JsonDataDescriptor potential CSV headers
     * 
     * @return String list of headers
     */
    public List<String> getHeaders() {
        List<String> tmpListStrings = new LinkedList<>();
        for (Pair<String, DataType> pair : listColumns) {
            tmpListStrings.add(pair.getKey());
        }
        return tmpListStrings;
    }

    public List<Pair<String, DataType>> getListColumns() {
        return listColumns;
    }
}
