package PollingPredictions.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import PollingPredictions.*;

import static PollingPredictions.CandidateInformation.CANDIDATES;

/*
    MAIN FOR GUI
 */

public class PollingData extends JPanel implements GUIConstants, Strategies {
    // location of text file containing list of urls
    private final static String URLS = "src/urls.txt";
    private final static boolean printSwitch = false;
    DataCollector dataCollector;
    Analyzer analyzer;
    ArrayList<Object[]> data;
    TablePanel tablePanel;
    JPanel[] statsPanels;
    DetailPanel detailPanel;


    // constructor that sets up all panels
    public PollingData() {
          /*
            WARNING: STRANGE GLITCH
            IF CODE DOES NOT WORK, YOU MUST CREATE A DATA FOLDER UNDER LAB4 FOLDER
            THANK YOU FOR UNDERSTANDING
         */

        // download data files
        dataCollector = new DataCollector(URLS);

        // parse data and put into data variable
        try {
            dataCollector.collectData();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        // to prevent unnecessary returning of data
        data = dataCollector.getData();

        // tablepanel
        tablePanel = new TablePanel(new PollTableModel(data));

        // statspanel
        analyzer = new Analyzer(printSwitch);
        for (StrategyAnalyzer strategy : STRATEGIES)
            analyzer.addStrategy(strategy);

        statsPanels = new JPanel[CANDIDATES.length + 1]; // +1 to include likely candidates
        int panel_iterator = 0;

        double mean = 0;
        double standard_deviation = 0;

        for (CandidateProfile candidate : CANDIDATES) {
            analyzer.analyze(candidate.candidate_id(), data);
            System.out.println(analyzer.getInstanceData());
            mean = (double) analyzer.getInstanceData().get("mean " + candidate.candidate_id());
            standard_deviation = (double) analyzer.getInstanceData()
                    .get("standard deviation " + candidate.candidate_id());

            statsPanels[panel_iterator] = new StatsPanel(candidate.candidate_id(), mean, standard_deviation);
            ++panel_iterator;
        }

        String likely_winner1 = "";
        String likely_winner2 = "";

        for (String key : analyzer.getInstanceData().keySet()) {
            if (key.contains("guess"))
                likely_winner1 = key;
            else if (key.contains("predict"))
                likely_winner2 = key;
        }
        statsPanels[panel_iterator] = new StatsPanel(likely_winner1, likely_winner2);

        // create detail panel
        detailPanel = new DetailPanel(data, tablePanel.dataTable);


        // create chart panel
        chartPanel chartPanel = new chartPanel("Pie Chart", mean);
        chartPanel.setVisible(true);
        chartPanel.setSize(CHART_SIZE);

        // add all panels
        // add table panel
        add(tablePanel.dataTable.getTableHeader(), BorderLayout.PAGE_START);
        add(tablePanel.scrollPane, BorderLayout.NORTH);
        for (JCheckBox checkBox : tablePanel.checkBoxes) {
            add(checkBox, BorderLayout.CENTER);
        }
        // add details panel
        add(detailPanel, BorderLayout.CENTER);
        // add stats panels
        for (JPanel statPanel : statsPanels)
            add(statPanel, BorderLayout.SOUTH);

        setBackground(BACKGROUND_COLOR);
        setVisible(true);
    }


    public static void main(String[] args) {
          /*
            WARNING: STRANGE GLITCH
            IF CODE DOES NOT WORK, YOU MUST CREATE A DATA FOLDER UNDER LAB4 FOLDER
            THANK YOU FOR UNDERSTANDING
         */

        // set up jframe
        JFrame jFrame = new JFrame();

        // set all defaults
        jFrame.setTitle(FRAME_TITLE);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        jFrame.getContentPane().add(new PollingData());

        jFrame.setVisible(true);
    }
}