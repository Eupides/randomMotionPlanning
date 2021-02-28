package Algorithm;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GraphCreator {
    private int gridSize;
    private Area confSpace;
    private HashMap<Integer, Point> points;
    private Random rand;
    private ArrayList<GraphEdge> edges;

    public GraphCreator(int gridSize, Area confSpace) {
        this.gridSize = gridSize;
        this.confSpace = confSpace;
        this.points = new HashMap<>();
        this.rand = new Random();
        this.edges = new ArrayList<>();
    }

    private void createRandomPoint(int range) {
        int x = this.rand.nextInt(this.gridSize);
        int y = this.rand.nextInt(this.gridSize);

        this.addPoint(x,y,range);
    }

    private boolean createRandomPointInArea(Point center, int range, int creationRange) {
        int x = this.rand.nextInt(creationRange*2) + (center.x-creationRange);
        int y = this.rand.nextInt(creationRange*2) + (center.y-creationRange);

        return this.addPoint(x,y,range);
    }

    public boolean addPoint(int x, int y, int range) {
        if(!confSpace.contains(x,y)) {
            Integer hash = x*100;
            hash += y;
            if(!points.containsKey(hash)) {
                Point newPoint = new Point(x,y);
                points.put(hash, newPoint);

                this.createEdgesOfPoint(newPoint, range);

                return true;
            }
        }
        return false;
    }

    public void createPoints(int maxPoints, int range) {
        while(this.points.size() < maxPoints) {
            this.createRandomPoint(range);
        }
    }

    public void addAdditionalPoints(int maxPoints, Point center, int range, int creationRange) {
        int pointCounter = 0;
        while(pointCounter < maxPoints) {
            if(this.createRandomPointInArea(center, range, creationRange)) {
                pointCounter++;
            }
        }
    }

    /**
     * creates edges
     * @param point
     * @param range circle around the point for "nearest" points
     * @return
     */
    public void createEdgesOfPoint(Point point, int range) {
        Integer hash;
        Point tempPoint;
        int xStart = ((point.x-range) >= 0) ? point.x-range : 0;
        int xMax = ((point.x+range) < this.gridSize) ? point.x+range : this.gridSize-1;
        int yStart = ((point.y-range) >= 0) ? point.y-range : 0;
        int yMax = ((point.y+range) < this.gridSize) ? point.y+range : this.gridSize-1;

        for(int i = xStart; i <= xMax; i++) {
            for(int j = yStart; j <= yMax; j++) {
                hash = (i*100) + j;
                if(this.points.containsKey(hash)) {
                    tempPoint = this.points.get(hash);
                    if(point.x != tempPoint.x || point.y != tempPoint.y) {
                        if(this.tryToConnectPoints(point, tempPoint)) {
                            GraphEdge edge = new GraphEdge(point, tempPoint);
                            if(!this.edges.contains(edge)) {
                                this.edges.add(edge);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean tryToConnectPoints(Point a, Point b) {
        double vx = b.x - a.x;
        double vy = b.y - a.y;

        double length = Math.sqrt(vx*vx + vy*vy);

        vx /= length;
        vy /= length;

        double newX;
        double newY;
        // ging für 26 80 und 27 85 oder so nicht
        double step = 0.5;
        for(double i = 0.5; i < length; i = i + step) {
            newX = a.x + (vx * i);
            newY = a.y + (vy * i);
            if(confSpace.contains(newX,newY)) {
                return false;
            }
        }

        return true;
    }

    public HashMap<Integer, Point> getPointMap() {
        return this.points;
    }

    public ArrayList<GraphEdge> getEdges() {
        return this.edges;
    }
}
