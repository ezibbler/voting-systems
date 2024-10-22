public class Main {

    public static void main(String[] args) {
        Election election = new Election(1024, 3);
        election.run("fptp");
    }
}
