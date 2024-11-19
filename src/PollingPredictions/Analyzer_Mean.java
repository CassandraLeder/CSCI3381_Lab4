package PollingPredictions;

/*
    Computes mean for analyzer
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyzer_Mean extends CandidateInformation implements StrategyAnalyzer, PollStructure  {
    private Map<Integer, Double> candidate_average;
    private double mean;

    public Analyzer_Mean() {
        candidate_average = new HashMap<Integer, Double>();
    }

    // setter
    public void setCandidateAverage(int candidate_id) {
        // error checking
        if (!isCandidateIDValid(candidate_id))
            throw new IllegalArgumentException("id: " + candidate_id + " is not a valid candidate id");
        if (getMean() <= 0)
            throw new IllegalArgumentException("Error: candidate average must be greater than zero");

        this.candidate_average.putIfAbsent(candidate_id, getMean());
    }
    public void setMean(double mean) { this.mean = mean; }

    // getter
    public Map<Integer, Double> getCandidateAverage() { return candidate_average; }
    public double getMean() { return mean; }

    // inherited methods
    @Override
    public Double returnInstanceData() {
        return(getMean());
    }
    @Override
    public void accept(int candidate_id, List<Object[]> data, AnalyzerVisitor visitor) {
        visitor.visit(candidate_id, data, this);
    }

    // find average of the percentage that the candidate has
    @Override
    public void analyzeData(int candidate_id, List<Object[]> data) {
        double sum = 0;
        int count = 0;

        for (Object[] lines : data) {
            for (Object line : lines) {
                if (line instanceof String line_str) {
                    String[] fields = line_str.split(STANDARD_DELIMITER);

                    if (Integer.parseInt(fields[final_data_headers.get("candidate id")]) == candidate_id) {
                        try {
                            sum += Double.parseDouble(fields[final_data_headers.get("percentage")]);
                            ++count;
                        } catch (NumberFormatException n) {
                            System.out.println(n.getMessage());
                        }
                    }
                }
            }
        }


        // avoiding dividing by zero
        if (count == 0)
            throw new IllegalArgumentException("Error: count must not be zero.");
        else {
            setMean(sum / count);
            setCandidateAverage(candidate_id);
        }
    }

    @Override
    public String toString() {
        return "mean";
    }
}