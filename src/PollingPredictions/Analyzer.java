package PollingPredictions;

/*
    This class will perform statistical calculations and analyze data about candidates
 */

import java.util.*;

public class Analyzer extends CandidateInformation implements PollStructure {
    private Map<String, Integer> candidate_points;
    private Map<String, Double> candidate_average;
    private Map<String, Double> standard_deviation;

    public Analyzer() {
        candidate_points = new HashMap<>();
        candidate_average = new HashMap<>();
        standard_deviation = new HashMap<>();
    }

    // setters
    public void setCandidatePoints(String candidate_last_name, int num) {
        // error checking
        if (!isCandidateLastNameValid(candidate_last_name))
            throw new IllegalArgumentException("Name: " + candidate_last_name + " is not a valid last name");
        if (num <= 0)
            throw new IllegalArgumentException("Error: number of points must be greater than zero");

        this.candidate_points.putIfAbsent(candidate_last_name, num);
    }

    public void setCandidateAverage(String candidate_last_name, double candidate_average) {
        // error checking
        if (!isCandidateLastNameValid(candidate_last_name))
            throw new IllegalArgumentException("Name: " + candidate_last_name + " is not a valid last name");
        if (candidate_average <= 0)
            throw new IllegalArgumentException("Error: candidate average must be greater than zero");

        this.candidate_average.putIfAbsent(candidate_last_name, candidate_average);
    }
    public void setStandardDeviation(String candidate_last_name, double standard_deviation) {
        // error checking
        if (!isCandidateLastNameValid(candidate_last_name))
            throw new IllegalArgumentException("Name: " + candidate_last_name + " is not a valid last name");
        if (standard_deviation <= 0)
            throw new IllegalArgumentException("Error: candidate average must be greater than zero");

        this.standard_deviation.putIfAbsent(candidate_last_name, standard_deviation);
    }


    // getters
    public Map<String, Integer> getCandidatePoints() { return candidate_points; }
    public Map<String, Double> getCandidateAverage() { return candidate_average; }
    public Map<String, Double> getStandardDeviation() { return standard_deviation; }

    /*
        CALCULATORS!
     */

    // find average of the percentage that the candidate has
    public Map<String,Double> computeAverage(int candidate_id, ArrayList<Object[]> data) {
        double sum = 0;
        int count = 0;

        // probably an easy way to do this with streams
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
        else
            setCandidateAverage(getCandidateProfile(candidate_id).candidate_last_name(),
                    sum / count);

        return(getCandidateAverage());
    }

    public Map<String, Double> computeStandardDeviation(int candidate_id, ArrayList<Object[]> data) {
        // find the mean associated with candidate id
        double mean = getCandidateAverage().get(getCandidateProfile(candidate_id).candidate_last_name());
        int count = 0;
        ArrayList<Double> deviations = new ArrayList<>();

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
        for (Double deviation : deviations) {
            variance += deviation;
        }
        variance /= count;

        // std dev is sqrt(variance)
        setStandardDeviation(getCandidateProfile(candidate_id).candidate_last_name(), Math.sqrt(variance));

        return getStandardDeviation();
    }

    // count the total number of times a candidate has been predicted,
    // that candidate must win according to the laws of nature and statistical bodies
    public String guessWinner(ArrayList<Object[]> data) {

        int[] points = new int[CANDIDATES.length];
        int candidate_index = 0;

        // initialize candidate points (points will be updated
        for (CandidateProfile candidate : CANDIDATES) {
            points[candidate_index] = 0;
            ++candidate_index;
        }

        candidate_index = 0;

        // repeated 3 times :^(
        // for each candidate
        for (CandidateProfile candidate : CANDIDATES) {
            for (Object[] lines : data) {
                for (Object line : lines) {
                    if (line instanceof String line_str) {
                        String[] fields = line_str.split(STANDARD_DELIMITER);

                        // if current candidate's name equals current data's candidate name
                        if (fields[final_data_headers.get("chosen candidate last name")]
                                .equalsIgnoreCase(candidate.candidate_last_name()))
                            ++points[candidate_index]; // add up points
                    }
                }
            }
            // update candidate points
            setCandidatePoints(candidate.candidate_last_name(), points[candidate_index]);
            ++candidate_index;
        }

        String winner = "";
        int max = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> set : getCandidatePoints().entrySet()) {
            if (set.getValue() > max) {
                max = set.getValue(); // points
                winner = set.getKey(); // candidate last name
            }
        }

        if (winner.isEmpty())
            throw new IllegalArgumentException("Error: no winner could be found.");

        return winner;
    }

    public String predictWinner() {
        String winner = "";
        double[] candidate_stats = new double[CANDIDATES.length];
        int candidate_index = 0;
        double max = Double.MIN_VALUE;

        // find probable candidate based on mean - std dev
        for (CandidateProfile candidate : CANDIDATES) {
            candidate_stats[candidate_index] =
                    getCandidateAverage().get(candidate.candidate_last_name())
                    - getStandardDeviation().get(candidate.candidate_last_name());

            if (candidate_stats[candidate_index] > max) {
                max = candidate_stats[candidate_index];
                winner = candidate.candidate_last_name();
            }

            ++candidate_index;
        }

        return(winner);
    }

    // not valid = false
    private boolean isCandidateLastNameValid(String candidate_last_name) throws IllegalArgumentException {
        boolean name_check_flag = false;

        for (CandidateProfile candidate : CANDIDATES) {
            if (candidate.candidate_last_name().equalsIgnoreCase(candidate_last_name)) {
                name_check_flag = true;
                break;
            }
        }

        return(name_check_flag);
    }
}
