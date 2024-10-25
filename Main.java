public class Main {

    public static void main(String[] args) {
        Election election = new Election(100_000, 8);
        election.run("fptp");
        election.run("ranked choice");
        election.run("borda");
    }
}
