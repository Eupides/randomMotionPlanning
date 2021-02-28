package Algorithm;

import java.awt.*;

public class DijkstraPoint {
    public Point point;
    public double distance;
    public Point pre;

    public DijkstraPoint(Point point, double distance, Point pre) {
        this.point = point;
        this.distance = distance;
        this.pre = pre;
    }
}
