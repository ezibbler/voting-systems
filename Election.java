public class Election {
    private Voter[] voters = new Voter[1024];
    private Voter[] candidates = new Voter[10];

    public Election() {
        initializeCandidates();
        initializeVoters();
    }

    private void initializeVoters() {
        for (int i = 0; i < voters.length; i++) {
            voters[i] = new Voter();
        }
    }

    private void initializeCandidates() {
        for (int i = 0; i < candidates.length; i++) {
            candidates[i] = new Voter();
        }
    }

    public int firstPastThePost() {
        int[] votes = new int[10];

        for (Voter v : voters) {
            double minDistance = Double.MAX_VALUE;
            int candidateIndex = -1;

            for (int c = 0; c < candidates.length; c++) {
                double distance = Voter.calculateIdeologicalDistance(v, candidates[c]);

                if (distance < minDistance) {
                    candidateIndex = c;
                    minDistance = distance;
                }

            }

            votes[candidateIndex]++;
        }

        int maxVotes = 0;
        int candidateIndex = -1;

        for (int i = 0; i < votes.length; i++) {
            if (votes[i] > maxVotes) {
                candidateIndex = i;
                maxVotes = votes[i];
            }
        }

        return candidateIndex;
    }
}
