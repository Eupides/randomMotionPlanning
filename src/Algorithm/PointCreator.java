package Algorithm;

import java.awt.*;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.Random;

public class PointCreator {
    int gridSize;
    Area confSpace;
    HashMap<Integer, Point> points;
    Random rand;

    public PointCreator(int gridSize, Area confSpace) {
        this.gridSize = gridSize;
        this.confSpace = confSpace;
        this.points = new HashMap<Integer, Point>();
        this.rand = new Random();
    }

    private void createRandomPoint() {
        int x = this.rand.nextInt(this.gridSize);
        int y = this.rand.nextInt(this.gridSize);

        if(!confSpace.contains(x,y)) {
            Integer hash = x*100;
            hash += y;
            if(!points.containsKey(hash)) {
                Point newPoint = new Point(x,y);
                points.put(hash, newPoint);
            }
        }
    }

    public void createPoints(int timeInSeconds, int maxPoints) {
        long t = System.currentTimeMillis();
        long end = t+(timeInSeconds*1000);
        while(System.currentTimeMillis() < end && this.points.size() < maxPoints) {
            this.createRandomPoint();
        }
    }

    public HashMap<Integer, Point> getPointMap() {
        return this.points;
    }
}
