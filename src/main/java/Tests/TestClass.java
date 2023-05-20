package Tests;


import logic.MainLogic;
import org.junit.Assert;
import org.junit.Test;

public class TestClass {
    @Test
    public void testTransport() {
        double[][] expectedResult = new double[][] {{10, 0, 10, 0}, {0, 28, 0, 2}, {0, 0, 17, 48}};
        double[] popyt = new double[] {10, 28, 27};
        double[] cena = new double[] {30, 25, 30};
        double[] podaz = new double[] {20, 30};
        double[] koszt = new double[] {10, 12};
        double[][] transport = new double[][] {{8, 14, 17},{12, 9, 19}};
        MainLogic mainLogic = new MainLogic(2, 3, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
        Assert.assertArrayEquals(actualResult, expectedResult);
    }
}
