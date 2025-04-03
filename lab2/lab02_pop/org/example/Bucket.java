package org.example;

public class Bucket {
    private int id;
    private int angle;
    private double volume;

    public Bucket(int id, int angle, double volume) {
        this.id = id;
        this.angle = angle;
        this.volume = volume;
    }
    public int getId() {
        return id;
    }
    public int getAngle() {
        return angle;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Bucket" + id +
                " angle=" + angle +
                " volume=" + volume + "\n";
    }
}
