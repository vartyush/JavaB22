package ru.stqa.pft.sandbox.sandbox;

public class Point {
    int x;
    int y;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p2) {
        double d = Math.sqrt((p2.x - this.x) ^ 2 + (p2.y - this.y) ^ 2);
        return d;
    }
}
