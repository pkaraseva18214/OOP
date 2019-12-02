package ru.nsu.fit.karaseva.calculator;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTests {

  @Test
  public void test1() {
    Calculator n = new Calculator();
    String str = "sin + - 1 2 1";
    Double res = n.calculator(str);
    Assert.assertEquals(0, res, 0.001);
  }

  @Test
  public void test2() {
    Calculator n = new Calculator();
    String str = "+ pow 2 3 5";
    Double res = n.calculator(str);
    Assert.assertEquals(13, res, 0.001);
  }

  @Test
  public void test3() {
    Calculator n = new Calculator();
    String str = "sqrt / + pow 2 3 92 4";
    Double res = n.calculator(str);
    Assert.assertEquals(5, res, 0.001);
  }

  @Test
  public void test4() {
    Calculator n = new Calculator();
    String str = "cos sin 0";
    Double res = n.calculator(str);
    Assert.assertEquals(1.000, res, 0.001);
  }

  @Test
  public void test5() {
    Calculator n = new Calculator();
    String str = "+ log - * 3 5 7 100";
    Double res = n.calculator(str);
    Assert.assertEquals(102.0794, res, 0.001);
  }

  @Test
  public void test6() {
    Calculator n = new Calculator();
    String str = "/ log - / pow 5 5 10 300 10";
    Double res = n.calculator(str);
    Assert.assertEquals(0.2525, res, 0.001);
  }
}
