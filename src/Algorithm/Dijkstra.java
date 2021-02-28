package Algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dijkstra {

    private GraphCreator graph;
    private HashMap<Integer, Point> points;
    private int range;
    private Double infiniteDistance = 9999.00;
    private HashMap<Integer, Integer> predecessors;
    private HashMap<Integer, Double> distances;
    private ArrayList<GraphEdge> usedEdges;
    private HashMap<Integer, Point> graphPoints;
    private Point end;

    public Dijkstra(GraphCreator graph, int range) {
        this.graph = graph;
        this.range = range;
        this.distances = new HashMap<>();
        this.predecessors = new HashMap<>();
        this.usedEdges = new ArrayList<>();
        this.points = new HashMap<>();
        this.graphPoints = new HashMap<>();
        this.end = new Point (99,99); //default point is 99;
    }

    public ArrayList<Point> createShortestPath() {
        ArrayList<Point> path = new ArrayList<>();
        path.add(this.end);
        Integer pointHash = (this.end.x*100) + this.end.y;

        while(this.predecessors.containsKey(pointHash) && this.predecessors.get(pointHash) != null) {
            Point predecessor = this.graphPoints.get(this.predecessors.get(pointHash));
            path.add(predecessor);

            pointHash = (predecessor.x*100) + predecessor.y;
        }

        return path;
    }

    public void tryConnecting(Point start, Point end) {
        this.initialize(start, end);

        while(this.points.size() > 0) {
            Integer nearestHash = this.getHashOfNearestPoint();
            Point nearestPoint = this.points.get(nearestHash);
            this.points.remove(nearestHash);
            ArrayList<GraphEdge> neighborEdges = this.getNeighborEdges(nearestPoint);

            for(GraphEdge neighbor : neighborEdges) {
                Integer neighborHash = this.getOtherPointFromEdge(nearestPoint, neighbor);

                if(this.points.containsKey(neighborHash)) {
                    this.updateDistances(nearestPoint, neighbor);
                }
            }
        }
    }

    private void initialize(Point start, Point end) {
        this.usedEdges = new ArrayList<>();
        this.predecessors = new HashMap<>();
        this.distances = new HashMap<>();
        this.points = new HashMap<>();
        this.end = end;

        graph.addPoint(start.x,start.y, this.range);
        graph.addPoint(end.x,end.y, this.range);

        this.graphPoints = graph.getPointMap();

        for(Map.Entry<Integer, Point> entry: this.graphPoints.entrySet()) {
            Integer hash = entry.getKey();
            Point point = entry.getValue();
            //deep copy?
            this.points.put(hash, new Point(point.x, point.y));
            this.distances.put(hash, this.infiniteDistance);
            this.predecessors.put(hash, null);

            if(point.x == start.x && point.y == start.y) {
                this.distances.put(hash, 0.00);
            }
        }
    }

    private Integer getOtherPointFromEdge(Point point, GraphEdge edge) {
        Integer neighborHash = 0;
        if(edge.a.x == point.x && edge.a.y == point.y) {
            neighborHash = (edge.b.x*100) + edge.b.y;
        } else if(edge.b.x == point.x && edge.b.y == point.y) {
            neighborHash = (edge.a.x*100) + edge.a.y;
        }

        return neighborHash;
    }

    private void updateDistances(Point point, GraphEdge neighborEdge) {
        Integer pointHash = (point.x*100) + point.y;
        Integer neighborHash = this.getOtherPointFromEdge(point, neighborEdge);

        Double alternative = this.distances.get(pointHash) + neighborEdge.cost;

        if(alternative < this.distances.get(neighborHash)) {
            this.distances.put(neighborHash, alternative);
            this.predecessors.put(neighborHash, pointHash);
        }
    }

    private ArrayList<GraphEdge> getNeighborEdges(Point point) {
        ArrayList<GraphEdge> edges = this.graph.getEdges();
        ArrayList<GraphEdge> neighborEdges = new ArrayList<>();

        for(GraphEdge edge : edges) {
            //TODO, funktioniert added tatsächlich?
            if(!this.usedEdges.contains(edge)) {
                if ((edge.a.x == point.x && edge.a.y == point.y) || (edge.b.x == point.x && edge.b.y == point.y)) {
                    neighborEdges.add(edge);
                    this.usedEdges.add(edge);
                }
            }
        }

        return neighborEdges;
    }

    private Integer getHashOfNearestPoint() {
        Integer lowestHash = null;
        Double lowestDistance = this.infiniteDistance;

        for(Map.Entry<Integer, Double> entry: this.distances.entrySet()) {
            Integer hash = entry.getKey();
            Double distance = entry.getValue();
            if(lowestHash == null && this.points.containsKey(entry.getKey())) {
                lowestHash = entry.getKey();
            } else if(distance < lowestDistance && this.points.containsKey(entry.getKey())){
                lowestDistance = distance;
                lowestHash = hash;
            }
        }

        return lowestHash;
    }
}
