import java.util.ArrayList;

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

public class Voter {
    private double[] beliefs = new double[16]; //a voter's beliefs are represented by an Array of values ranging from 0.0 to 1.0, thereby constituting an ideology vector
    private String name;

    public String getName() {
        return name;
    }

    public Voter(double[] beliefs) {
        this.beliefs = beliefs;
    }

    public Voter() {
        for (int i = 0; i < beliefs.length; i++) {
            beliefs[i] = Math.random();
        }
        try {
            name = scanFile().get((int) (Math.random() * 1001));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public double[] getBeliefs() {
        return beliefs;
    }

    public static double calculateIdeologicalDistance(Voter v1, Voter v2) {
        double distance = 0.0;

        for (int i = 0; i < v1.getBeliefs().length; i++) {
            distance += Math.pow(v1.getBeliefs()[i] -v2.getBeliefs()[i], 2);
        }

        return Math.abs(Math.sqrt(distance));
    }

    private static ArrayList<String> scanFile() throws FileNotFoundException {
        File file = new File("last_names.txt");
        Scanner input = new Scanner(file);
        ArrayList<String> names = new ArrayList<String>();

        while(input.hasNext()) {
            String nextLine = input.nextLine();
            names.add(nextLine);
        }
        
        input.close();
        return names;
        
    }
}