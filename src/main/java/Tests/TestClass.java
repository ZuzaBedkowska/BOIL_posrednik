package Tests;


import logic.MainLogic;
import org.junit.Assert;
import org.junit.Test;

public class TestClass {
    @Test
    public void testTransportExample() {
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

    @Test
    public void testZeros() {
        double[][] expectedResult = new double[][] {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        double[] popyt = new double[] {0, 0, 0};
        double[] cena = new double[] {0, 0, 0};
        double[] podaz = new double[] {0, 0};
        double[] koszt = new double[] {0, 0};
        double[][] transport = new double[][] {{0, 0, 0},{0, 0, 0}};
        MainLogic mainLogic = new MainLogic(2, 3, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
        Assert.assertArrayEquals(actualResult, expectedResult);
    }

    @Test
    public void testIfProfitZero() {
        double[][] expectedResult = new double[][] {{10, 0, 10, 0}, {0, 28, 2, 0}, {0, 0, 15, 50}};
        double[] popyt = new double[] {10, 28, 27};
        double[] cena = new double[] {30, 25, 30};
        double[] podaz = new double[] {20, 30};
        double[] koszt = new double[] {10, 12};
        double[][] transport = new double[][] {{8, 14, 17},{12, 9, 18}};
        MainLogic mainLogic = new MainLogic(2, 3, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
        Assert.assertArrayEquals(actualResult, expectedResult);
    }

    @Test
    public void testExample1(){
        double[][] expectedResult = new double[][] {{5, 30, 10}, {25, 0, 0}, {0, 0, 60}};
        double[] popyt = new double[] {30, 30};
        double[] cena = new double[] {12, 13};
        double[] podaz = new double[] {45, 25};
        double[] koszt = new double[] {6, 7};
        double[][] transport = new double[][] {{7, 4},{3, 5}};
        MainLogic mainLogic = new MainLogic(2, 2, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
        Assert.assertArrayEquals(actualResult, expectedResult);
    }
    @Test
    public void testExample2() { //check if it just works
        //double[][] expectedResult = new double[][] {{10, 0, 10, 0}, {0, 28, 0, 2}, {0, 0, 17, 48}};
        double[] popyt = new double[] {16, 12, 24};
        double[] cena = new double[] {18, 16, 15};
        double[] podaz = new double[] {20, 40};
        double[] koszt = new double[] {7, 8};
        double[][] transport = new double[][] {{4, 7, 2},{8, 10, 4}};
        MainLogic mainLogic = new MainLogic(2, 3, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        mainLogic.getOptimal_transport();
        //Assert.assertArrayEquals(actualResult, expectedResult);
    }
    @Test
    public void testExample3(){
        //double[][] expectedResult = new double[][] {{10, 0, 10, 0}, {0, 28, 0, 2}, {0, 0, 17, 48}};
        double[] popyt = new double[] {28, 37, 24};
        double[] cena = new double[] {30, 30, 25};
        double[] podaz = new double[] {20, 30, 55};
        double[] koszt = new double[] {10, 12, 14};
        double[][] transport = new double[][] {{17, 15, 6},{7, 7, 1}, {15, 14, 3}};
        MainLogic mainLogic = new MainLogic(3, 3, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
        //Assert.assertArrayEquals(actualResult, expectedResult);
    }
    @Test
    public void testExample4(){
        //double[][] expectedResult = new double[][] {{10, 0, 10, 0}, {0, 28, 0, 2}, {0, 0, 17, 48}};
        double[] popyt = new double[] {20, 30};
        double[] cena = new double[] {11, 13};
        double[] podaz = new double[] {35, 25};
        double[] koszt = new double[] {6, 7};
        double[][] transport = new double[][] {{6, 4},{2, 5}};
        MainLogic mainLogic = new MainLogic(2, 2, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
        //Assert.assertArrayEquals(actualResult, expectedResult);
    }
    @Test
    public void testExample5(){
        double[] popyt = new double[] {15, 12, 18};
        double[] cena = new double[] {15, 14, 16};
        double[] podaz = new double[] {20, 30};
        double[] koszt = new double[] {6, 9};
        double[][] transport = new double[][] {{5, 3, 8},{9, 2, 4}};
        MainLogic mainLogic = new MainLogic(2, 3, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
    }
    @Test
    public void testExample6() {
        //double[][] expectedResult = new double[][] {{10, 0, 10, 0}, {0, 28, 0, 2}, {0, 0, 17, 48}};
        double[] popyt = new double[] {25, 35};
        double[] cena = new double[] {11, 13};
        double[] podaz = new double[] {30, 20};
        double[] koszt = new double[] {6, 7};
        double[][] transport = new double[][] {{6, 4},{2, 5}};
        MainLogic mainLogic = new MainLogic(2, 2, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
        //Assert.assertArrayEquals(actualResult, expectedResult);
    }

    @Test
    public void testExample7(){
        double[] popyt = new double[] {15, 12, 18};
        double[] cena = new double[] {15, 14, 16};
        double[] podaz = new double[] {20, 20};
        double[] koszt = new double[] {6, 9};
        double[][] transport = new double[][] {{4, 7, 2},{8, 10, 4}};
        MainLogic mainLogic = new MainLogic(2, 3, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
    }
    @Test
    public void testExample8(){
        double[] popyt = new double[] {30, 30};
        double[] cena = new double[] {12, 13};
        double[] podaz = new double[] {45, 25};
        double[] koszt = new double[] {6, 7};
        double[][] transport = new double[][] {{7, 4},{3, 5}};
        MainLogic mainLogic = new MainLogic(2, 2, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
    }

    @Test
    public void testExample9(){
        double[] popyt = new double[] {18, 32, 20};
        double[] cena = new double[] {15, 14, 16};
        double[] podaz = new double[] {20, 30};
        double[] koszt = new double[] {6, 9};
        double[][] transport = new double[][] {{5, 3, 8},{9, 2, 4}};
        MainLogic mainLogic = new MainLogic(2, 3, podaz, popyt, koszt, cena, transport);
        mainLogic.calc_maxProfitRule();
        double[][] actualResult = mainLogic.getOptimal_transport();
    }
}
