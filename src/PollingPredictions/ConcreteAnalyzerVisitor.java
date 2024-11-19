package PollingPredictions;

import java.util.List;

public class ConcreteAnalyzerVisitor implements AnalyzerVisitor {
    private boolean print_switch;

    // constructor
    public ConcreteAnalyzerVisitor(boolean print_switch) {
        this.print_switch = print_switch;
    }

    public void decidePrint(int candidate_id, StrategyAnalyzer strategy) {
        if (print_switch) {
            AnalyzerPrinter printer = new AnalyzerPrinter();

            if (strategy instanceof Analyzer_Mean)
                printer.print(candidate_id, (Analyzer_Mean) strategy);
            else if (strategy instanceof Analyzer_StandardDeviation)
                printer.print(candidate_id, (Analyzer_StandardDeviation) strategy);
            else if (strategy instanceof Analyzer_Guess)
                printer.print(candidate_id, (Analyzer_Guess) strategy);
            else if (strategy instanceof Analyzer_Predict)
                printer.print(candidate_id, (Analyzer_Predict) strategy);
        }
    }

    @Override
    public void visit(int candidate_id, List<Object[]> data, Analyzer_Mean analyzerMean) {
        analyzerMean.analyzeData(candidate_id, data);
        decidePrint(candidate_id, analyzerMean);
    }

    @Override
    public void visit(int candidate_id, List<Object[]> data, Analyzer_StandardDeviation analyzerStandardDeviation) {
        analyzerStandardDeviation.analyzeData(candidate_id, data);
        decidePrint(candidate_id, analyzerStandardDeviation);
    }

    @Override
    public void visit(int candidate_id, List<Object[]> data, Analyzer_Guess analyzerGuess) {
        analyzerGuess.analyzeData(candidate_id, data);
        decidePrint(candidate_id, analyzerGuess);
    }

    @Override
    public void visit(int candidate_id, List<Object[]> data, Analyzer_Predict analyzerPredict) {
        analyzerPredict.analyzeData(candidate_id, data);
        decidePrint(candidate_id, analyzerPredict);
    }
}
