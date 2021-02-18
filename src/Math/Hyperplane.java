package Math;

import java.util.ArrayList;

public class Hyperplane {

    private int dimensions;
    private ArrayList<Integer> points;

    public Hyperplane(int dimensions, ArrayList<Integer> points) {
        if(dimensions != points.size()) {
            System.out.println("Mistake in creating a hyperplane, points must be same as dimensions");
        }
        this.dimensions = dimensions;
        this.points = points;
    }
}
