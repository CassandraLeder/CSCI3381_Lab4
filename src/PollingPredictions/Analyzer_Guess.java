package PollingPredictions;
/*
    Simplistically guess winner of election for analyzer
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyzer_Guess extends CandidateInformation implements StrategyAnalyzer, PollStructure {
    private Map<Integer, Integer> candidate_points;
    private String winner;

    public Analyzer_Guess() {
        candidate_points = new HashMap<Integer, Integer>();
    }

    // setters
    public void setCandidatePoints(int candidate_id, int num) {
        // error checking
        if (!isCandidateIDValid(candidate_id))
            throw new IllegalArgumentException("id: " + candidate_id + " is not a candidate id");
        if (num <= 0)
            throw new IllegalArgumentException("Error: number of points must be greater than zero");

        this.candidate_points.putIfAbsent(candidate_id, num);
    }
    public void setWinner(String winner) { this.winner = winner; }

    // getters
    public Map<Integer, Integer> getCandidatePoints() { return candidate_points; }
    public String getWinner() {
        return winner;
    }

    // inherited methods
    @Override
    public String returnInstanceData() { return getWinner(); }

    @Override
    public void accept(int candidate_id, List<Object[]> data, AnalyzerVisitor visitor) {
        visitor.visit(candidate_id, data, this);
    }
    @Override
    public void analyzeData(int candidate_id, List<Object[]> data) {
        // count the total number of times a candidate has been predicted,
        // that candidate must win according to the laws of nature and statistical bodies
        int[] points = new int[CANDIDATES.length];
        int candidate_index = 0;

        // initialize candidate points (points will be updated
        for (CandidateProfile candidate : CANDIDATES) {
            points[candidate_index] = 0;
            ++candidate_index;
        }

        candidate_index = 0;

        // repeated 3 times :^(
        // for each candidate
        for (CandidateProfile candidate : CANDIDATES) {
            for (Object[] lines : data) {
                for (Object line : lines) {
                    if (line instanceof String line_str) {
                        String[] fields = line_str.split(STANDARD_DELIMITER);

                        // if current candidate's name equals current data's candidate name
                        if (fields[final_data_headers.get("chosen candidate last name")]
                                .equalsIgnoreCase(candidate.candidate_last_name()))
                            ++points[candidate_index]; // add up points
                    }
                }
            }
            // update candidate points
            setCandidatePoints(candidate.candidate_id(), points[candidate_index]);
            ++candidate_index;
        }

        String winner = "";
        int max = Integer.MIN_VALUE;

        for (Map.Entry<Integer, Integer> set : getCandidatePoints().entrySet()) {
            if (set.getValue() > max) {
                max = set.getValue(); // points
                setWinner(getCandidateProfile(set.getKey()).candidate_last_name()); // candidate last name
            }
        }

        if (getWinner().isEmpty())
            throw new IllegalArgumentException("Error: no winner could be found.");
    }

    @Override
    public String toString() {
        return "guess";
    }
}