package org.example;

public class Results {

    private int place;
    private int angle;
    private double height;

    public Results(int id, int angle, double height) {
        this.place = id;
        this.angle = angle;
        this.height = height;
    }

    public int getPlace() {
        return place;
    }
    public int getAngle() {
        return angle;
    }
    public double getHeight() {
        return height;
    }
    public void setPlace(int place) {
        this.place = place;
    }
    public void setAngle(int angle) {
        this.angle = angle;
    }
    public void setHeight(double height) {
        this.height = height;
    }


    @Override
    public String toString() {
        return place + ", " + angle + ", " + height + "\n";
    }
}
