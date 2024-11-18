package PollingPredictions;
/*
    - Define what headers in data from csv is
    - Define what the data extracted from csv should look like
    - contains csv delimiter
 */
import java.util.Map;
import java.util.TreeMap;

public interface PollStructure {
    // prevent making typos by rewriting thrice
    String[] STANDARD_COLUMNS = {"pollster name", "chosen candidate last name", "candidate id", "percentage"};

    // standard columns for header
    TreeMap<String, Integer> original_data_headers = new TreeMap<>(Map.of(STANDARD_COLUMNS[0], 2,
            STANDARD_COLUMNS[1], 48,
            STANDARD_COLUMNS[2], 49,
            STANDARD_COLUMNS[3], 51));

    // this map is only used to iterate over in order to query original header in parser
    TreeMap<Integer, String> new_data_headers = new TreeMap<>(Map.of(0, STANDARD_COLUMNS[0],
            1, STANDARD_COLUMNS[1],
            2, STANDARD_COLUMNS[2],
            3, STANDARD_COLUMNS[3]));

    // what columns should look like when data is finished being parsed
    TreeMap<String,Integer> final_data_headers = new TreeMap<>(Map.of(STANDARD_COLUMNS[0], 0,
            STANDARD_COLUMNS[1], 1,
            STANDARD_COLUMNS[2], 2,
            STANDARD_COLUMNS[3], 3));

    // this should really be a char for clarity, but it's unnecessary/costly to do String.valueof() every time
    String STANDARD_DELIMITER = ",";
}
