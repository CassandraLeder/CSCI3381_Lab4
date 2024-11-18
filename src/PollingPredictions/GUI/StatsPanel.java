package PollingPredictions.GUI;
/*
    Panel that displays stats, mean and std deviation, about presidential election candidates
 */

import javax.swing.*;
import PollingPredictions.CandidateProfile;
import PollingPredictions.CandidateInformation;
import java.util.Formatter;

public class StatsPanel extends JPanel implements GUIConstants {
    JTextArea mean;
    JTextArea standard_deviation;
    JTextArea likelyCandidate1;
    JTextArea likelyCandidate2;

    StatsPanel(int candidate_id, double mean, double standard_deviation) {
        // this is the class containing info about candidates
        CandidateInformation candidateInformation = new CandidateInformation();
        CandidateProfile current_candidate =  candidateInformation.getCandidateProfile(candidate_id);

        // panel
        setSize(STATS_PANEL_DIMENSION);

        // mean
        // only display percentage to 2 points
        Formatter formatter = new Formatter();
        Formatter mean_formatted = formatter.format("%.2f", mean);

        this.mean = new JTextArea("The mean percentage for " + current_candidate.candidate_first_name() + " "
                + current_candidate.candidate_last_name() + " is " + mean_formatted + "%.");
        this.mean.setSize(JTEXTBOX_DIMENSION);
        this.mean.setFont(DEFAULT_FONT);
        this.mean.setBackground(BACKGROUND_COLOR);

        // standard deviation
        // format std dev to 2 places
        formatter = new Formatter();
        Formatter stddev_formatted = formatter.format("%.2f", standard_deviation);
        this.standard_deviation = new JTextArea("The standard deviation for " + current_candidate.candidate_first_name() + " "
                + current_candidate.candidate_last_name()  + " is " + stddev_formatted + ".");
        this.standard_deviation.setSize(JTEXTBOX_DIMENSION);
        this.standard_deviation.setFont(DEFAULT_FONT);
        this.standard_deviation.setBackground(BACKGROUND_COLOR);

        formatter.close();

        super.add(this.mean);
        super.add(this.standard_deviation);

        setBackground(BACKGROUND_COLOR);
        setVisible(true);
    }

    // this constructor is to prevent likely candidates being displayed more than once (other constructor runs more than once)
    StatsPanel(String likelyCandidate1, String likelyCandidate2) {
        // first likely candidate
        this.likelyCandidate1 = new JTextArea("Based on a simplistic method the 2024 president elect will be " + likelyCandidate1 + ".");
        this.likelyCandidate1.setSize(JTEXTBOX_DIMENSION);
        this.likelyCandidate1.setFont(DEFAULT_FONT);
        this.likelyCandidate1.setBackground(BACKGROUND_COLOR);

        // second likely candidate
        this.likelyCandidate2 = new JTextArea("Based on a more advanced method the 2024 president elect will be " + likelyCandidate2 + ".");
        this.likelyCandidate2.setSize(JTEXTBOX_DIMENSION);
        this.likelyCandidate2.setFont(DEFAULT_FONT);
        this.likelyCandidate2.setBackground(BACKGROUND_COLOR);

        super.add(this.likelyCandidate1);
        super.add(this.likelyCandidate2);

        setBackground(BACKGROUND_COLOR);
        setVisible(true);
    }
}