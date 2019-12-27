package ru.nsu.fit.karaseva.primeNumbers;

import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class PrimeNumbersTests {
  @Test
  public void test1() throws InterruptedException {
    List<Integer> numbers = new LinkedList<>();
    numbers.add(6);
    numbers.add(8);
    numbers.add(7);
    numbers.add(13);
    numbers.add(4);
    numbers.add(9);
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(!p.testIteratively());
    Assert.assertFalse(!p.parallel());
  }

  @Test
  public void test2() throws InterruptedException {
    List<Integer> numbers = new LinkedList<>();
    numbers.add(6997901);
    numbers.add(6997927);
    numbers.add(6997937);
    numbers.add(6997967);
    numbers.add(6998009);
    numbers.add(6998029);
    numbers.add(6998039);
    numbers.add(6998051);
    numbers.add(6998053);
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(p.testIteratively());
    Assert.assertFalse(p.parallel());
  }

  @Test
  public void test3() throws InterruptedException {
    List<Integer> numbers = new LinkedList<>();
    numbers.add(1);
    numbers.add(3);
    numbers.add(5);
    numbers.add(7);
    numbers.add(101);
    numbers.add(333);
    numbers.add(1111111);
    numbers.add(303521);
    numbers.add(666);
    numbers.add(74859);
    numbers.add(5101);
    numbers.add(37);
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(!p.testIteratively());
    Assert.assertFalse(!p.parallel());
  }

  @Test
  public void test4() throws InterruptedException {
    List<Integer> numbers = new LinkedList<>();
    try{
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    } catch (NullPointerException ignore){}
  }

  @Test
  public void test5() throws InterruptedException {
    List<Integer> numbers = new LinkedList<>();
    numbers.add(1153);
    numbers.add(1447);
    numbers.add(2803);
    numbers.add(313);
    numbers.add(1259);
    numbers.add(2161);
    numbers.add(2351);
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(p.testIteratively());
    Assert.assertFalse(p.parallel());
  }

  @Test
  public void test6() throws InterruptedException {
    List<Integer> numbers = new LinkedList<>();
    numbers.add(55550);
    numbers.add(89998);
    numbers.add(7455521);
    numbers.add(0);
    numbers.add(-45);
    PrimeNumbers p = new PrimeNumbers(numbers);
    p.parallel();
    Assert.assertFalse(!p.testIteratively());
    Assert.assertFalse(!p.parallel());
  }
}
