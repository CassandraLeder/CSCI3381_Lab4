package PollingPredictions.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import PollingPredictions.Analyzer;
import PollingPredictions.CandidateProfile;
import PollingPredictions.DataCollector;
import static PollingPredictions.CandidateInformation.CANDIDATES;

/*
    MAIN FOR GUI
 */

public class PollingData extends JPanel implements GUIConstants {
    // location of text file containing list of urls
    private final static String URLS = "src/urls.txt";
    DataCollector dataCollector;
    Analyzer analyzer;
    ArrayList<Object[]> data;
    TablePanel tablePanel;
    JPanel[] statsPanels;
    DetailPanel detailPanel;


    // constructor that sets up all panels
    public PollingData() {
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
        analyzer = new Analyzer();
        statsPanels = new JPanel[CANDIDATES.length + 1]; // +1 to include likely candidates
        int panel_iterator = 0;

        for (CandidateProfile candidate : CANDIDATES) {
            // get should be used on returned data because the maps are numbers associated with a candidate's last name
            double mean = analyzer.computeAverage(candidate.candidate_id(), data).get(candidate.candidate_last_name());
            double standard_deviation = analyzer.computeStandardDeviation(candidate.candidate_id(), data).get(candidate.candidate_last_name());

            statsPanels[panel_iterator] = new StatsPanel(candidate.candidate_id(), mean, standard_deviation);

            ++panel_iterator;
        }

        String likely_winner1 = analyzer.guessWinner(data);
        String likely_winner2 = analyzer.predictWinner();
        statsPanels[panel_iterator] = new StatsPanel(likely_winner1, likely_winner2);

        // create detail panel
        detailPanel = new DetailPanel(data, tablePanel.dataTable);


        // create chart panel
        chartPanel chartPanel = new chartPanel("Pie Chart", analyzer);
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