import java.lang.reflect.Array;

public class Voter {
    private double[] beliefs = new double[16]; //a voter's beliefs are represented by an Array of values ranging from 0.0 to 1.0, thereby constituting an ideology vector

    public Voter(double[] beliefs) {
        this.beliefs = beliefs;
    }

    public Voter() {
        for (int i = 0; i < beliefs.length; i++) {
            beliefs[i] = Math.random();
        }
    }

    public double[] getBeliefs() {
        return beliefs;
    }

    public static double calculateIdeologicalDistance(Voter v1, Voter v2) {
        double distance = 0.0;

        for (int i = 0; i < v1.getBeliefs().length; i++) {
            distance += Math.pow(v1.getBeliefs()[i], 2);
            distance += Math.pow(v2.getBeliefs()[i], 2);
        }

        return Math.sqrt(distance);
    }
}