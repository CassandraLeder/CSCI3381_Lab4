package PollingPredictions;
/*
    Calculate standard deviation for analyzer
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyzer_StandardDeviation extends CandidateInformation implements StrategyAnalyzer, PollStructure {
    private Map<Integer, Double> standardDeviations;
    private double standardDeviation;

    public Analyzer_StandardDeviation() {
        standardDeviations = new HashMap<>();
    }

    public void setStandardDeviation(double standardDeviation) {
        if (standardDeviation > 0)
            this.standardDeviation = standardDeviation;
    }

    public void setStandardDeviations(int candidate_id) {
        // error checking
        if (!isCandidateIDValid(candidate_id))
            throw new IllegalArgumentException("id: " + candidate_id + " is not a valid candidate id");
        if (getStandardDeviation() <= 0)
            throw new IllegalArgumentException("Error: standard deviation must be greater than zero");
        standardDeviations.putIfAbsent(candidate_id, getStandardDeviation());
    }

    public double getStandardDeviation() { return standardDeviation; }
    public Map<Integer, Double> getStandardDeviations() { return standardDeviations; }


    // inherited methods
    @Override
    public Double returnInstanceData() {
        return(getStandardDeviation());
    }
    @Override
    public void accept(int candidate_id, List<Object[]> data, AnalyzerVisitor visitor) {
        visitor.visit(candidate_id, data, this);
    }
    @Override
    public void analyzeData(int candidate_id, List<Object[]> data) {
        Analyzer_Mean analyze_mean = new Analyzer_Mean();
        analyze_mean.analyzeData(candidate_id, data);

        // find the mean associated with candidate id
        double mean = analyze_mean.getCandidateAverage()
                .get(candidate_id);
        int count = 0;
        java.util.ArrayList<Double> deviations = new ArrayList<>();

        // find distance of each data point from the mean
        for (Object[] lines : data) {
            for (Object line : lines) {
                if (line instanceof String line_str) {
                    String[] fields = line_str.split(STANDARD_DELIMITER);

                    if (Integer.parseInt(fields[final_data_headers.get("candidate id")]) == candidate_id) {

                        // the distance of a data point from the mean squared
                        deviations.add((Double.parseDouble(fields[final_data_headers.get("percentage")]) - mean)
                                * (Double.parseDouble(fields[final_data_headers.get("percentage")]) - mean));
                        ++count;
                    }
                }
            }
        }

        // variance is the mean of deviation
        double variance = 0;
        for (Double deviation : deviations)
            variance += deviation;

        variance /= count;

        // std dev is sqrt(variance)
        setStandardDeviation(Math.sqrt(variance));
        setStandardDeviations(getCandidateProfile(candidate_id).candidate_id());
    }

    @Override
    public String toString() {
        return "standard deviation";
    }
}