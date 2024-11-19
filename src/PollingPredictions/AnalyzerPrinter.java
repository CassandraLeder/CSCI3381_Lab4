package PollingPredictions;
/*
    Print all analyzers
 */

public class AnalyzerPrinter extends CandidateInformation implements Printer {
    @Override
    public void print(int candidate_id, Analyzer_Mean analyzerMean) {
        CandidateProfile candidate = getCandidateProfile(candidate_id);
        System.out.println("Candidate " + candidate.candidate_first_name() + " " + candidate.candidate_last_name()
                + " percent average is " +
                analyzerMean.getCandidateAverage().get(candidate_id) + "%");
    }

    @Override
    public void print(int candidate_id, Analyzer_StandardDeviation analyzerStandardDeviation) {
        CandidateProfile candidate = getCandidateProfile(candidate_id);

        System.out.println("Candidate " + candidate.candidate_first_name() + " " + candidate.candidate_last_name()
                + " standard deviation is " +
                analyzerStandardDeviation.getStandardDeviations().get(candidate_id));
    }

    @Override
    public void print(int candidate_id, Analyzer_Guess analyzerGuess) {
        System.out.println("Simplisticly we assume that the winner of the 2024 presidential election is " +
                analyzerGuess.getWinner());
    }

    @Override
    public void print(int candidate_id, Analyzer_Predict analyzerPredict) {
        System.out.println("More accurately the president will be..... " +
                analyzerPredict.getWinner());
    }
}