package PollingPredictions;

import java.util.List;

public class Analyzer_Predict extends CandidateInformation implements StrategyAnalyzer, PollStructure {
    private String winner;

    void setWinner(String winner) { this.winner = winner; }
    String getWinner() {
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
        Analyzer_Mean analyze_mean = new Analyzer_Mean();
        Analyzer_StandardDeviation analyze_standardDeviation = new Analyzer_StandardDeviation();

        double[] candidate_stats = new double[CANDIDATES.length];
        int candidate_index = 0;
        double max = Double.MIN_VALUE;

        for (CandidateProfile candidate : CANDIDATES) {
            analyze_mean.analyzeData(candidate.candidate_id(), data);
            analyze_standardDeviation.analyzeData(candidate.candidate_id(), data);
        }

        // find probable candidate based on mean - std dev
        for (CandidateProfile candidate : CANDIDATES) {
            candidate_stats[candidate_index] =
                    analyze_mean.getCandidateAverage().get(candidate.candidate_id())
                            - analyze_standardDeviation.getStandardDeviations().get(candidate.candidate_id());

            if (candidate_stats[candidate_index] > max) {
                max = candidate_stats[candidate_index];
                setWinner(candidate.candidate_last_name());
            }

            ++candidate_index;
        }
    }

    @Override
    public String toString() {
        return "predict";
    }
}