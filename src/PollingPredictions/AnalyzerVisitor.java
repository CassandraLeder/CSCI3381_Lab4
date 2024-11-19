package PollingPredictions;

import java.util.List;

public interface AnalyzerVisitor {
    void visit(int candidate_id, List<Object[]> data, Analyzer_Mean analyzerMean);
    void visit(int candidate_id, List<Object[]> data, Analyzer_StandardDeviation analyzerStandardDeviation);
    void visit(int candidate_id, List<Object[]> data, Analyzer_Guess analyzerGuess);
    void visit(int candidate_id, List<Object[]> data, Analyzer_Predict analyzerPredict);
}