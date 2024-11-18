package PollingPredictions;

/*
    This class downloads files from the interwebs,
    then reads the input, adds it to data variable
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataCollector extends CandidateInformation implements PollStructure {
    // relevant information from polls
    // (see PollStructure to learn about the labeling of data's columns)
    private ArrayList<Object[]> data;
    private ArrayList<String> file_names;

    public DataCollector(String url_location) {
        // initialize
        data = new ArrayList<>();
        file_names = new ArrayList<>();
        downloadFiles(url_location);
    }

    // helper function for constructor
    // downloads files from specified urls (urls.txt by default)
    private void downloadFiles(String url_location) {
        // try to create a new file_reader
        // probably an easier way to do this with streams (just saying)
        try (BufferedReader file_reader = new BufferedReader(new FileReader(url_location))) {
            String url;

            // for each url in file,
            while ((url = file_reader.readLine()) != null) {

                // try to open url,
                try {
                    URI uri = URI.create(url);
                    InputStream poll = uri.toURL().openStream();

                    // if successful, create new data file
                    String url_name = "data/" + Parser.parseURL(url);
                    Files.copy(poll, Paths.get(url_name), StandardCopyOption.REPLACE_EXISTING);
                    file_names.add(url_name);
                } catch (IOException e) { // if url failed to open
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) { // if text file did not open
            System.out.println(e.getMessage());
        }
    }

    // collect data from csvs
    public void collectData() throws IOException {
        // for all files
        for (String file : file_names) {
            // puts every line of csv into string contents
            String contents = Files.readString(Path.of(file), StandardCharsets.UTF_8);
            // every line is seperated by newlines
            List<String> lines = List.of(contents.split("\n"));

            // parses data and adds to class' data variable
            data = lines.stream()
                    .skip(1) // skip header
                    .map(line -> line.split(STANDARD_DELIMITER)) // split into strings based on delimiter
                    .map(Parser::parseData) // parse data
                    .filter(parsed_data -> parsed_data.length > 0) // filter out empty strings
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    public ArrayList<Object[]> getData() { return data; }
    public ArrayList<String> getFileNames() { return file_names; }
}