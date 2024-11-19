package PollingPredictions;

public interface Printer {
    void print(int candidate_id, Analyzer_Mean analyzerMean);
    void print(int candidate_id, Analyzer_StandardDeviation analyzerStandardDeviation);
    void print(int candidate_id, Analyzer_Guess analyzerGuess);
    void print(int candidate_id, Analyzer_Predict analyzerPredict);
}
