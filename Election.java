import java.util.ArrayList;

public class Election {
    private Voter[] voters = new Voter[1024];
    private Voter[] candidates = new Voter[10];

    private final double[] NEUTRALITY = new double[]{0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5};
    private final Voter NEUTRAL_VOTER = new Voter(NEUTRALITY);

    public Election() {
        initializeCandidates();
        initializeVoters();
    }

    public Election(int numVoters, int numCandidates) {
        voters = new Voter[numVoters];
        candidates = new Voter[numCandidates];

        initializeCandidates();
        initializeVoters();
    }

    private void initializeVoters() {
        for (int i = 0; i < voters.length; i++) {
            voters[i] = new Voter(false);
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

            case "ranked choice":
                result = rankedChoice();
                break;

            case "borda":
                result = bordaCount();
                break;

            case "two round":
                result = twoRound();
                break;

            default:
                result = "Not a valid election type";
        }

        System.out.println(result);
    }

    private String firstPastThePost() {
        int[] votes = new int[candidates.length];

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

        for (int i = 0; i < votes.length; i++) {
            if (votes[i] > maxVotes) {
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
            voteData[i][0] = candidates[i].getName();
            voteData[i][1] = String.valueOf(votes[i]);
            voteData[i][2] = String.valueOf(((int) 1000.0 * votes[i] / voteTotal) / 10.0);

        }
        Table table = new Table(headers, voteData);

        return table.getTable();
    }

    private String rankedChoice() {
        /*
         * round 1: fptp
         * subsequently: weakest candidate is dropped and their votes are added to their voters' preferred candidates
         * 
         * how to implement:
         * create 2d adjacency matrix: matrix[voter][candidate] representing their ideological distance values
         */

        double[][] ideologyMatrix = new double[voters.length][candidates.length];
        int[][] votes = new int[candidates.length][candidates.length];

        //create voter adjacency matrix
        for (int v = 0; v < voters.length; v++) {
            for (int c = 0; c < candidates.length; c++) {
                ideologyMatrix[v][c] = Voter.calculateIdeologicalDistance(voters[v], candidates[c]);
            }
        }

        boolean raceOver = false;
        int finalRound = 0;

        for (int round = 0; round <= candidates.length && !raceOver; round++) {
            finalRound = round;

            for (int v = 0; v < voters.length; v++) {
                int candidateIndex = -1;
                double minDistance = Double.MAX_VALUE;
                for (int c = 0; c < candidates.length; c++) {
                    if (ideologyMatrix[v][c] < minDistance) {
                        candidateIndex = c;
                        minDistance = ideologyMatrix[v][c];
                    }
                }

                votes[round][candidateIndex]++;

                if (votes[round][candidateIndex] >= (voters.length + 2) / 2) {
                    raceOver = true;
                }
            }

            if (!raceOver) {
                //find losing candidate

                int candidateIndex = -1;
                int minVotes = Integer.MAX_VALUE;

                for (int c = 0; c < candidates.length; c++) {
                    if (votes[round][c] < minVotes && votes[round][c] > 0) {
                        candidateIndex = c;
                        minVotes = votes[round][c];
                    }
                }
                
                //reatribute their votes
                for (int v = 0; v < voters.length; v++) {
                    ideologyMatrix[v][candidateIndex] = Double.MAX_VALUE;
                }
            }
        }

        //String[] headers = new String[]{"Candidate", "Votes", "%"};
        ArrayList<String> headers = new ArrayList<String>();

        headers.add("Candidate");

        for (int r = 0; r <= finalRound; r++) {
            headers.add("Round " + r);
            headers.add("%");
        }

        String[] newHeaders = new String[headers.size()];

        for (int h = 0; h < headers.size(); h++) {
            newHeaders[h] = headers.get(h);
        }

        String[][] voteData = new String[votes.length][headers.size()];

        int voteTotal = 0;

        for (int vote : votes[finalRound]) {
            voteTotal += vote;
        }  

        

        for (int i = 0; i < votes.length; i++) {
            voteData[i][0] = candidates[i].getName() /*"(" + Math.round() Voter.calculateIdeologicalDistance(NEUTRAL_VOTER, candidates[i]) + ")"*/;

            for (int r = 0; r <= finalRound; r += 1) {
                voteData[i][2 * r + 1] = String.valueOf(votes[r][i]);
                voteData[i][2 * r + 2] = String.valueOf(((int) 1000.0 * votes[r][i] / voteTotal) / 10.0);
            }

        }

        Table table = new Table(newHeaders, voteData);

        return table.getTable();

    }

    private String bordaCount() {
        //voters give all candidates votes in order
        //each candidate gets (n-their rank) votes from each voter

        int[] votes = new int[candidates.length];

        for (int v = 0; v < voters.length; v++) {
            double[] ideologyDifferences = new double[candidates.length];
            for (int c = 0; c < candidates.length; c++) {
                ideologyDifferences[c] = Voter.calculateIdeologicalDistance(voters[v], candidates[c]);
            }

            for (int p = 1; p < candidates.length; p++) {
                double minDistance = Double.MAX_VALUE;
                int candidateIndex = -1;
                for (int c = 0; c < candidates.length; c++) {
                    if (ideologyDifferences[c] < minDistance) {
                        minDistance = ideologyDifferences[c];
                        candidateIndex = c;
                    }
                }
                votes[candidateIndex] += candidates.length - p;
                ideologyDifferences[candidateIndex] = Double.MAX_VALUE;
            }
            
        }

        int maxVotes = 0;

        for (int i = 0; i < votes.length; i++) {
            if (votes[i] > maxVotes) {
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
            voteData[i][0] = candidates[i].getName();
            voteData[i][1] = String.valueOf(votes[i]);
            voteData[i][2] = String.valueOf(((int) 1000.0 * votes[i] / voteTotal) / 10.0);

        }
        Table table = new Table(headers, voteData);

        return table.getTable();
    }

    private String twoRound() {
        double[][] ideologyMatrix = new double[voters.length][candidates.length];
        int[][] votes = new int[2][candidates.length];

        //create voter adjacency matrix
        for (int v = 0; v < voters.length; v++) {
            for (int c = 0; c < candidates.length; c++) {
                ideologyMatrix[v][c] = Voter.calculateIdeologicalDistance(voters[v], candidates[c]);
            }
        }

        boolean raceOver = false;

        for (int v = 0; v < voters.length; v++) {
            int candidateIndex = -1;
            double minDistance = Double.MAX_VALUE;
            for (int c = 0; c < candidates.length; c++) {
                if (ideologyMatrix[v][c] < minDistance) {
                    candidateIndex = c;
                    minDistance = ideologyMatrix[v][c];
                }
            }

            votes[0][candidateIndex]++;

            if (votes[0][candidateIndex] >= (voters.length + 2) / 2) {
                raceOver = true;
            }
        }

        if (raceOver) {
            //TODO: return table
        }

        //find top two candidates

        int firstCandidateVotes = 0;
        int firstCandidateIndex = -1;

        int secondCandidateVotes = 0;
        int secondCandidateIndex = -1;
        for (int c = 0; c < candidates.length; c++) {
            if (votes[0][c] > secondCandidateVotes) {
                if (votes[0][c] > firstCandidateVotes) {
                    secondCandidateVotes = firstCandidateVotes;
                    secondCandidateIndex = firstCandidateIndex;

                    firstCandidateVotes = votes[0][c];
                    firstCandidateIndex = c;
                    
                    
                } else {
                    secondCandidateVotes = votes[0][c];
                    secondCandidateIndex = c;
                }
            }
        }

        //place ideology difference for other candidates at MAX_VALUE

        for (int v = 0; v < voters.length; v++) {
            for (int c = 0; c < candidates.length; c++) {
                if (c == firstCandidateIndex || c == secondCandidateIndex) {
                    continue;
                }
                ideologyMatrix[v][c] = Double.MAX_VALUE;
            }
        }

        //run election again
        for (int v = 0; v < voters.length; v++) {
            int candidateIndex = -1;
            double minDistance = Double.MAX_VALUE;
            for (int c = 0; c < candidates.length; c++) {
                if (ideologyMatrix[v][c] < minDistance) {
                    candidateIndex = c;
                    minDistance = ideologyMatrix[v][c];
                }
            }

            votes[1][candidateIndex]++;
        }

        ArrayList<String> headers = new ArrayList<String>();

        headers.add("Candidate");

        for (int r = 0; r < (raceOver ? 1 : 2); r++) {
            headers.add("Round " + r);
            headers.add("%");
        }

        String[] newHeaders = new String[headers.size()];

        for (int h = 0; h < headers.size(); h++) {
            newHeaders[h] = headers.get(h);
        }

        String[][] voteData = new String[candidates.length][headers.size()];

        int voteTotal = 0;

        for (int vote : votes[(raceOver ? 0 : 1)]) {
            voteTotal += vote;
        }  

        

        for (int i = 0; i < candidates.length; i++) {
            voteData[i][0] = candidates[i].getName() /*"(" + Math.round() Voter.calculateIdeologicalDistance(NEUTRAL_VOTER, candidates[i]) + ")"*/;

            for (int r = 0; r < (raceOver ? 1 : 2); r += 1) {
                voteData[i][2 * r + 1] = String.valueOf(votes[r][i]);
                voteData[i][2 * r + 2] = String.valueOf(((int) 1000.0 * votes[r][i] / voteTotal) / 10.0);
            }

        }

        Table table = new Table(newHeaders, voteData);

        return table.getTable();
    }
}
