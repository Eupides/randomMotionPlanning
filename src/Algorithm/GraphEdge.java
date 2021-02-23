package Algorithm;

import java.awt.*;

public class GraphEdge {
    public Point a;
    public Point b;
    public double cost;
    /**
     *
     * @param a
     * @param b
     */
    public GraphEdge(Point a, Point b) {
        this.setPointsAccordingToPosition(a,b);
        this.setCost(a,b);
    }

    private void setCost(Point a, Point b) {
        double vx = b.x - a.x;
        double vy = b.y - a.y;
        this.cost = Math.sqrt(vx*vx + vy*vy);
    }

    /**
     * decides which point is lower, if same height-> lower x value
     * @return boolean
     */
    private void setPointsAccordingToPosition(Point a, Point b) {
        if(a.y < b.y) {
            this.a = a;
            this.b= b;
        } else if(a.y == b.y) {
            if(a.x <= b.x) {
                this.a = a;
                this.b= b;
            } else {
                this.a = b;
                this.b = a;
            }
        } else {
            this.a = b;
            this.b = a;
        }
    }
}
