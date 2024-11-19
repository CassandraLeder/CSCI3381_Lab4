package PollingPredictions;

/*
    Keep list of all strategies
 */

public interface Strategies {
    // list of all different stats for analysis
    StrategyAnalyzer[] STRATEGIES = new StrategyAnalyzer[]{new Analyzer_Mean(),
            new Analyzer_StandardDeviation(), new Analyzer_Guess(), new Analyzer_Predict()};
}
