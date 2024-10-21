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

    public String firstPastThePost() {
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

        return electionTable(votes);
    }

    private String electionTable(int[] votes) {
        final String TABLE_HEADER = "-".repeat(13);
        final String COLUMNS = "| votes | % |";
        String tableContent = "";
        int voteTotal = 0;

        for (int vote : votes) {
            voteTotal += vote;
        }

        for (int i = 0; i < votes.length; i++) {
            final double VOTE_PERCENTAGE = ((int) 1000.0 * votes[i] / voteTotal) / 10.0;
            tableContent += "| " + i + " | " + VOTE_PERCENTAGE + " |\n";
        }
        /* Displays table in the format of:
         * --------
         * | v | % |
         * | c
         * | c
         * | c
         * | c
         *  --------
         */

        return TABLE_HEADER + "\n" + COLUMNS + "\n" + tableContent + TABLE_HEADER;
    }
}
