package PollingPredictions.GUI;

/*
    This class creates a pie chart based on the average percentage of each presidential candidate
    This code is based on the tutorial here: https://www.tutorialspoint.com/jfreechart/jfreechart_pie_chart.htm.
 */

import javax.swing.JPanel;
import PollingPredictions.Analyzer;
import PollingPredictions.CandidateProfile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import java.awt.*;

import static PollingPredictions.CandidateInformation.CANDIDATES;

public class chartPanel extends ApplicationFrame implements GUIConstants {
    // oops... my class is also called chartPanel
    // this is JFreeChart class
    ChartPanel chartPanel;

    // create new application frame
    public chartPanel(String title, Analyzer analyzer) {
        // set up application frame
        super(title);
        setContentPane(getChartPanel(analyzer));
    }

    // creates a data set of the average percentages of each candidate
    private PieDataset createDataSet(Analyzer analyzer) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // create data points from each candidate's mean
        for (CandidateProfile candidate : CANDIDATES) {
            Double mean = analyzer.getCandidateAverage().get(candidate.candidate_last_name());

            // get the double from the average map
            dataset.setValue(candidate.candidate_first_name() + " " + candidate.candidate_last_name(),
                    mean);
        }

        return (PieDataset) dataset;
    }

    // create new JFreeChart
    private JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart("Candidate Average of Percentage",
                dataset,
                true, // include legend
                true, // create tool tips
                false); // do not generate urls
        return chart;
    }

    // create chart and return it
    public JPanel getChartPanel(Analyzer analyzer) {
        JFreeChart chart = createChart(createDataSet(analyzer));
        chartPanel = new ChartPanel(chart);

        // this is for color formatting
        PiePlot plot = (PiePlot) chart.getPlot();
        int candidate_index = 0;

        // for slice of pie, set blue for democrat and red for republican
        for (CandidateProfile candidate : CANDIDATES) {
            if (candidate.party().equalsIgnoreCase("REP")) {
                plot.setSectionPaint(candidate_index, Color.red);
            }
            else if (candidate.party().equalsIgnoreCase("DEM")) {
                plot.setSectionPaint(candidate_index, Color.blue);
            }

            candidate_index++;
        }

        return chartPanel;
    }
}