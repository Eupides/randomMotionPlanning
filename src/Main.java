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
        Rectangle mid = new Rectangle(25,50,26,11);
        Rectangle right = new Rectangle(60,50,36,31);

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

        Scanner scan = new Scanner(System.in);
        GraphCreator pointC = new GraphCreator(100, grid);

        /*pointC.points.put(9499,new Point(94,99));

        pointC.createEdgesOfPoint(new Point(92,0),10);
        ArrayList<GraphEdge> edges = pointC.getEdges();*/


        System.out.println("sekundenanzahl für erstellung der punkte");
        int seconds = scan.nextInt();
        System.out.println("Maxpunkte: ");
        int maxPoints = scan.nextInt();
        pointC.createPoints(seconds, maxPoints, 15);

        HashMap<Integer, Point> points = pointC.getPointMap();
        System.out.println(points.size());

        ArrayList<GraphEdge> edges = pointC.getEdges();
        System.out.println(edges.size());

        for(GraphEdge edge : edges) {
            if(edge.a.y < edge.b.y) {
                if(edge.b.y - edge.a.y > 15) {
                    System.out.println("wrong: A("+edge.a.x+","+edge.a.y+") B("+edge.b.x+","+edge.b.y+")");
                }
            } else {
                if(edge.a.y - edge.b.y > 15) {
                    System.out.println("wrong: A("+edge.a.x+","+edge.a.y+") B("+edge.b.x+","+edge.b.y+")");
                }
            }
        }

        String graphic = TikZForms.getPointString(points);
        graphic += TikZForms.getTikZRectanglesString(rects);
        graphic += TikZForms.getEdgeString(edges);
        try{
            TexFileWriter.writeTexFile("C:\\Users\\stret\\Documents\\uni\\WS2021\\geo_sem\\arbeit\\grafiken\\points.tex",graphic);
        } catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }
}
