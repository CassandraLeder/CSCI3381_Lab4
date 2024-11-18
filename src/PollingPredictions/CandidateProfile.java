package PollingPredictions;

public record CandidateProfile(String candidate_first_name, String candidate_last_name,
                               String vice_president_first_name, String vice_president_last_name,
                               String party,
                               int candidate_id) {}