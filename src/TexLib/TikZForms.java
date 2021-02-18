package TexLib;

import java.awt.*;
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
            double leftUpperCornerY = ((rect.getY() + height) / TikZForms.scale)-0.1;
            double rightLowerCornerX = ((rect.getX() + width)/ TikZForms.scale)-0.1;
            double rightLowerCornerY = rect.getY() / TikZForms.scale;

            output += "\\draw[" + obstacleColor + "," + obstacleLineThickness + "] (" + leftUpperCornerX + "," + leftUpperCornerY + ") rectangle (" + rightLowerCornerX + "," + rightLowerCornerY + ");";
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
}