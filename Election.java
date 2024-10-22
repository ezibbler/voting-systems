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

    public void run(String electionType) {
        String result = "";
        switch (electionType) {
            case "fptp": case "first past the post":
                result = firstPastThePost();
                break;
        
            default:
                result = "Not a valid election type";
        }

        System.out.println(result);
    }

    private String firstPastThePost() {
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

        String[] headers = new String[]{"Candidate", "Votes", "%"};

        String[][] voteData = new String[votes.length][headers.length];

        int voteTotal = 0;

        for (int vote : votes) {
            voteTotal += vote;
        }

        for (int i = 0; i < votes.length; i++) {
            voteData[i][0] = String.valueOf(i);
            voteData[i][1] = String.valueOf(votes[i]);
            voteData[i][2] = String.valueOf(((int) 1000.0 * votes[i] / voteTotal) / 10.0);

        }
        Table table = new Table(headers, voteData);

        return table.getTable();
    }
}
