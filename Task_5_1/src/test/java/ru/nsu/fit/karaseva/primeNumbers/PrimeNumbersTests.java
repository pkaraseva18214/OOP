package ru.nsu.fit.karaseva.primeNumbers;

import org.junit.Assert;
import org.junit.Test;

public class PrimeNumbersTests {
  @Test
  public void test1() throws InterruptedException {
    int[] numbers = {6, 8, 7, 13, 4, 9};
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(!p.testIteratively());
    Assert.assertFalse(!p.parallel());
  }

  @Test
  public void test2() throws InterruptedException {
    int[] numbers = {6997901, 6997927, 6997937, 6997967, 6998009, 6998039, 6998051, 6998053};
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(p.testIteratively());
    Assert.assertFalse(p.parallel());
  }

  @Test
  public void test3() throws InterruptedException {
    int[] numbers = {1, 3, 5, 7, 101, 333, 111111, 303521, 666, 74859, 5101, 37};
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(!p.testIteratively());
    Assert.assertFalse(!p.parallel());
  }

  @Test
  public void test4() {
    int[] numbers = {};
    try {
      PrimeNumbers p = new PrimeNumbers(numbers);
      p.parallel();
    } catch (NullPointerException ignore) {
    }
  }

  @Test
  public void test5() {
    int[] numbers = {1153, 1447, 2803, 313, 1259, 2161, 2351};
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(p.testIteratively());
    Assert.assertFalse(p.parallel());
  }

  @Test
  public void test6() {
    int[] numbers = {55550, 89998, 7455521, 0, -45};
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(!p.testIteratively());
    Assert.assertFalse(!p.parallel());
  }
}
