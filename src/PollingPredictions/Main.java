package PollingPredictions;

/*
    Poll-Machine Project
    Created by Cassandra Leder on 23-10-24

    This project will receive polling info from project 538, create data/charts based on data,
    create predictions from this information.

*/

import java.io.IOException;

public class Main extends CandidateInformation {
    // location of text file containing list of urls
    final static String URLS = "src/urls.txt";


    public static void main(String[] args) {
        DataCollector dataCollector = new DataCollector(URLS);
        try {
            dataCollector.collectData();
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        Analyzer analyzer = new Analyzer();

        for (CandidateProfile candidate : CANDIDATES) {
            // print out the average for each candidate
            System.out.println("Candidate " + candidate.candidate_first_name() +  " " + candidate.candidate_last_name()
                    + " percent average is " +
                    analyzer.computeAverage(candidate.candidate_id(),
                    dataCollector.getData()).get(candidate.candidate_last_name()) + "%");

            // print out standard deviation for each candidate
            System.out.println("Candidate " + candidate.candidate_first_name() + " " + candidate.candidate_last_name()
                            + " standard deviation is " +
                    analyzer.computeStandardDeviation(candidate.candidate_id(),
                    dataCollector.getData()).get(candidate.candidate_last_name()));
        }

        System.out.println("Simplisticly we assume that the winner of the 2024 presidential election is " +
                analyzer.guessWinner(dataCollector.getData()));

        System.out.println("More accurately the president will be..... " +
                analyzer.predictWinner());
    }
}