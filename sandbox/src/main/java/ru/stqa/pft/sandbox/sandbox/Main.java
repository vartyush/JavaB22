package ru.stqa.pft.sandbox.sandbox;



public class Main {

    public static void main(String[] args) {
        Point point=new Point(2,4);
        Point point2=new Point(4,10);

        System.out.println("Расстояние между двумя точками = " + point.distance(point2));
    }

// Вызов функции:
//
//    public static void main(String[] args) {
//               System.out.println("Расстояние между двумя точками = " + distance(new Point(2,4), new Point(4,10)));
//    }
//
  // Функция:
//
//    public static double distance(Point p1, Point p2) {
//        double d = Math.sqrt((p2.x - p1.x) ^ 2 + (p2.y - p1.y) ^ 2);
//        return d;
//    }
}

