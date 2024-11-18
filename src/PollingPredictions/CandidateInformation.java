package PollingPredictions;

/*
    This class is shared by PollingPredictions.DataCollector and PollingPredictions.Analyzer classes to share the candidates const
*/

import java.util.Arrays;
import java.util.List;

public class CandidateInformation {
    // only real candidates worth looking at are Harris and Trump
    public final static CandidateProfile[] CANDIDATES = {new CandidateProfile("Kamala", "Harris",
            "Tim", "Walz", "DEM", 16661),
            new CandidateProfile("Donald", "Trump",
                    "JD", "Vance", "REP", 16651)};

    // get a candidate profile
    public CandidateProfile getCandidateProfile(int candidate_id) {

        List<CandidateProfile> candidate = Arrays.stream(CANDIDATES)
                .filter(s -> s.candidate_id() == candidate_id)
                .toList();

        // if no candidate found
        if (candidate.isEmpty())
            throw new RuntimeException("Candidate not found");
        if (candidate.size() > 1)
            throw new RuntimeException("Multiple candidate found");

        // size == 1
        return candidate.getFirst();
    }
}