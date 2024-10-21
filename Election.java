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
}
