public class Main {

    public static void main(String[] args) {
        Election election = new Election(1024, 8);
        election.run("fptp");
        election.run("ranked choice");
    }
}
