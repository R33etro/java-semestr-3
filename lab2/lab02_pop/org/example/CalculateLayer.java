package org.example;

public class CalculateLayer {
    public static double calculateRadius(double radius1, double angle) {
        double volume = 1; //portion of sand
        double radius2 = 0.0;
        double precision = 0.1;
        double low = 0.0;
        double high = radius1;

        while((high - low) > precision) {
            radius2 = (low+high)/2;
            double height = (radius1 - radius2)*Math.tan(Math.toRadians(angle));
            double v = 0.333*(Math.PI)*height*(radius1*radius1 + radius1*radius2 + radius2*radius2);
            if(v < volume) {
                high = radius2;
            } else {
                low = radius2;
            }
        }
        return Math.max(radius2, 0.0);
    }

    public static double calculateHeight(double radius1, double radius2, double angle) {
        double height = (radius1 - radius2)*(Math.tan(Math.toRadians(angle)));
        return Math.max(height, 0.0);
    }

    public static double calculateHeightOfTriangle(double radius1, double angle) {
        double height = (radius1/2.0)*(Math.tan(Math.toRadians(angle)));
        return Math.max(height, 0.0);
    }
}
