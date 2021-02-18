import Algorithm.PointCreator;
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
        Rectangle mid = new Rectangle(25,50,21,11);
        Rectangle right = new Rectangle(60,50,21,31);

        ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
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
        PointCreator pointC = new PointCreator(100, grid);
        System.out.println("sekundenanzahl für erstellung der punkte");
        int seconds = scan.nextInt();
        System.out.println("Maxpunkte: ");
        int maxPoints = scan.nextInt();
        pointC.createPoints(seconds, maxPoints);

        HashMap<Integer, Point> points = pointC.getPointMap();
        System.out.println(points.size());


        String graphic = TikZForms.getPointString(points);
        graphic += TikZForms.getTikZRectanglesString(rects);
        try{
            TexFileWriter.writeTexFile("C:\\Users\\stret\\Documents\\uni\\WS2021\\geo_sem\\arbeit\\grafiken\\points.tex",graphic);
        } catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
