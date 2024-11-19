package PollingPredictions;
/*
    Implements strategy design pattern for Analyzer class.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static PollingPredictions.CandidateInformation.CANDIDATES;

public interface StrategyAnalyzer {
    // to be modified
    void accept(int candidate_id, List<Object[]> data, AnalyzerVisitor visitor);
    void analyzeData(int candidate_id, List<Object[]> data);
    @Override String toString();
    Object returnInstanceData();

    // not valid = false
    default boolean isCandidateIDValid(int candidate_id) throws IllegalArgumentException {
        boolean name_check_flag = false;

        for (CandidateProfile candidate : CANDIDATES) {
            if (candidate.candidate_id() == candidate_id) {
                name_check_flag = true;
                break;
            }
        }

        return(name_check_flag);
    }
}