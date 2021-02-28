import Algorithm.Dijkstra;
import Algorithm.GraphCreator;
import Algorithm.GraphEdge;
import TexLib.TikZForms;
import Util.TexFileWriter;

import java.awt.*;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Max Area: 100 x 100");
        System.out.println("Create Obstacles");

        Rectangle left = new Rectangle(0,50,21,11);
        Rectangle mid = new Rectangle(25,50,31,11);
        Rectangle right = new Rectangle(60,50,38,31);

        ArrayList<Rectangle> rects = new ArrayList<>();
        rects.add(left);
        rects.add(mid);
        rects.add(right);

        Area grid = new Area();
        Area rectangleBottomLeft = new Area(left);
        Area rectangleTopRight = new Area(mid);
        Area rectangleMiddle = new Area(right);

        grid.add(rectangleBottomLeft);
        grid.add(rectangleTopRight);
        grid.add(rectangleMiddle);


        // go over multiple times
        int range = 10;
        int extraScan = 1;
        int maxPoints = 250;
        int extraPoints = maxPoints/20;
        int successes = 0;
        int failures = 0;
        int tries = 1000;
        Point start = new Point(50, 5);
        Point end = new Point(50, 90);
        //first ist 50,5 ... 50,90
        //second ist 50,5 ... 10 90;
        //third ist 10,10 ... 90,90

        HashMap<Integer, Point> points = new HashMap<>();
        ArrayList<GraphEdge> edges = new ArrayList<>();
        ArrayList<Point> shortestPath = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        for(int i = 0; i < tries; i++) {
            GraphCreator graph = new GraphCreator(100, grid);

            if(extraScan == 1) {
                //create less points so they can be added later
                graph.createPoints(maxPoints-extraPoints, range);

                Point leftPoint = new Point(23, 55);
                graph.addAdditionalPoints(extraPoints/2, leftPoint, range, 15);
                Point rightPoint = new Point(58, 60);
                graph.addAdditionalPoints(extraPoints/2, rightPoint, range, 15);
            } else {
                graph.createPoints(maxPoints, range);
            }

            Dijkstra dijkstra = new Dijkstra(graph, range);
            dijkstra.tryConnecting(start, end);

            if(dijkstra.createShortestPath().size() == 1) {
                failures++;
            } else {
                successes++;
                points = graph.getPointMap();
                edges = graph.getEdges();
                shortestPath = dijkstra.createShortestPath();
            }

            if(i % 100 == 0) {
                System.out.println(i);
            }
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime/1000);

        System.out.println("Failures: "+failures);
        System.out.println("Successes: "+successes);


        String graphic = TikZForms.getPointString(points);
        graphic += TikZForms.getBorderOfGridString(100, 100);
        graphic += TikZForms.getTikZRectanglesString(rects);
        graphic += TikZForms.getEdgeString(edges);
        //graphic += TikZForms.getTikZWorkingPathString(shortestPath);
        try{
            TexFileWriter.writeTexFile("C:\\Users\\stret\\Documents\\uni\\WS2021\\geo_sem\\arbeit\\grafiken\\roadmap.tex",graphic);
        } catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
