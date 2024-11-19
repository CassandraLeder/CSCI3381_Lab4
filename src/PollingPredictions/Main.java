package PollingPredictions;

/*
    Poll-Machine Project
    Created by Cassandra Leder on 23-10-24

    This project will receive polling info from project 538, create data/charts based on data,
    create predictions from this information.

*/

import java.io.IOException;

public class Main extends CandidateInformation implements Strategies {
    // location of text file containing list of urls
    final static String URLS = "src/urls.txt";
    final static boolean printSwitch = true;

    public static void main(String[] args) {
        /*
            WARNING: STRANGE GLITCH
            IF CODE DOES NOT WORK, YOU MUST CREATE A DATA FOLDER UNDER LAB4 FOLDER
            THANK YOU FOR UNDERSTANDING
         */
        DataCollector dataCollector = new DataCollector(URLS);
        try {
            dataCollector.collectData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Analyzer analyzer = new Analyzer(printSwitch);

        try {
            dataCollector.collectData();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }


        // populate analyzer with all strategies
        for (StrategyAnalyzer strategy : STRATEGIES)
            analyzer.addStrategy(strategy);


        for (CandidateProfile candidate : CANDIDATES)
            analyzer.analyze(candidate.candidate_id(), dataCollector.getData());

        System.out.println(analyzer.getInstanceData());
    }
}