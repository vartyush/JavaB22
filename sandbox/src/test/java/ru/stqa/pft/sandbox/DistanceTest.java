package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.sandbox.Point;

public class DistanceTest {
    @Test

    public void distanceTest(){
        Point point1=new Point(4,7);
        Point point2 = new Point(6,8);
        Assert.assertEquals(point1.distance(point2), 1.6);
    }
}
