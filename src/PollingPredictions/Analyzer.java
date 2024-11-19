package PollingPredictions;

/*
    This class will perform statistical calculations and analyze data about candidates
 */

import java.util.*;

public class Analyzer {
    private List<StrategyAnalyzer> strategies;
    private Boolean printSwitch;
    private Map<String, Object> instanceData;

    public Analyzer(boolean printSwitch) {
        this.strategies = new ArrayList<>();
        this.instanceData = new HashMap<>();
        this.printSwitch = printSwitch;
    }

    public void addStrategy(StrategyAnalyzer strategy) {
        if (strategy != null)
            this.strategies.add(strategy);
    }

    private void addInstanceData(String key, Object value) {
        if (value != null)
            instanceData.putIfAbsent(key, value);
    }

    public Map<String, Object> getInstanceData() { return instanceData; }

    public void analyze(int candidate_id, List<Object[]> data) {
        ConcreteAnalyzerVisitor visitor = new ConcreteAnalyzerVisitor(printSwitch);

        for (StrategyAnalyzer strategy : strategies) {
            strategy.accept(candidate_id, data, visitor);
            addInstanceData(strategy.toString() + " " + candidate_id, strategy.returnInstanceData());
        }
    }
}