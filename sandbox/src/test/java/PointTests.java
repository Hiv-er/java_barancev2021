import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void pointLengthTest() {
        Point p1 = new Point(8, -1);
        Point p2 = new Point(4, 2);

        Assert.assertEquals(p1.distance(p2), 5.0);
    }

    @Test
    public void pointLengthTest2() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(10, 4);

        Assert.assertEquals(p1.distance(p2), 10.770329614269007);
    }

    @Test
    public void pointLengthTest3() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);

        Assert.assertEquals(p1.distance(p2), 0);
    }
}
