package TexLib;

import Algorithm.GraphEdge;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class TikZForms {
    public static double scale = 10;

    public static String getTikZRectanglesString(ArrayList<Rectangle> rects) {
        String obstacleColor = "blue";
        String obstacleLineThickness = "very thick";
        String output = "";

        for(int i=0; i < rects.size(); i++) {
            Rectangle rect = rects.get(i);
            double height = rect.getHeight();
            double width = rect.getWidth();

            double leftUpperCornerX = rect.getX() / TikZForms.scale;
            double leftUpperCornerY = ((rect.getY() + height) / TikZForms.scale)-0.05;
            double rightLowerCornerX = ((rect.getX() + width)/ TikZForms.scale)-0.05;
            double rightLowerCornerY = rect.getY() / TikZForms.scale;

            output += "\\draw[" + obstacleColor + "," + obstacleLineThickness + "] (" + leftUpperCornerX + "," + leftUpperCornerY + ") rectangle (" + rightLowerCornerX + "," + rightLowerCornerY + ");";
        }

        return output;
    }

    public static String getTikZWorkingPathString(ArrayList<Point> points) {
        String output = "\\draw[green] ";
        for(int i = 0; i < points.size(); i++) {
            if(i == points.size() - 1) {
                output += " (" + points.get(i).x/TikZForms.scale + "," + points.get(i).y/TikZForms.scale + ");";
            } else {
                output += " (" + points.get(i).x/TikZForms.scale + "," + points.get(i).y/TikZForms.scale + ") --";
            }
        }
        return output;
    }

    public static String getPointString(HashMap<Integer, Point> points) {
        String output = "";
        for(Point point : points.values()) {
            output += "\\filldraw [gray] ("+point.x/TikZForms.scale+","+point.y/TikZForms.scale+") circle (2pt);";
        }

        return output;
    }

    public static String getEdgeString(ArrayList<GraphEdge> edges) {
        String output = "";
        for(GraphEdge edge : edges) {
            output += "\\draw ("+edge.a.x/TikZForms.scale+","+edge.a.y/TikZForms.scale+") -- ("+edge.b.x/TikZForms.scale+","+edge.b.y/TikZForms.scale+");";
        }

        return output;
    }

    public static String getBorderOfGridString(int x, int y) {
        String output = "\\draw (0,0) -- ("+x/TikZForms.scale+",0);"; // unten
        output += "\\draw (0,0) -- (0,"+y/TikZForms.scale+");"; // links
        output += "\\draw ("+ x/TikZForms.scale +",0) -- ("+x/TikZForms.scale+","+y/TikZForms.scale+");"; // rechts
        output += "\\draw (0,"+x/TikZForms.scale+") -- ("+x/TikZForms.scale+","+y/TikZForms.scale+");"; // oben

        return output;
    }
}
