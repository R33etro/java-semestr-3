package org.example;

public class Place {
    private int id;
    private double radius;

    public Place(int id, double radius) {
        this.id = id;
        this.radius = radius;
    }
    public int getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Place" + id +
                " radius=" + radius + "\n";
    }
}
