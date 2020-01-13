package ru.nsu.fit.karaseva.stack;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class Tests {

  @Test
  public void test1() {
    Stack<Integer> stack;
    stack = new Stack<Integer>();
    ArrayList<Integer> arr = new ArrayList<Integer>();
    for (int i = 1; i < 100; ++i) {
      if ((i * 5) / 4 == 0) {
        stack.push(i);
        arr.add(i);
      } else {
        if (arr.size() != 0) {
          int elementArr = arr.remove(arr.size() - 1);
          int elementStack = stack.pop();
          Assert.assertEquals(elementArr, elementStack);
        }
      }
    }
    Assert.assertEquals(arr.size(), stack.amountOfElements());
  }

  @Test
  public void test2() {
    Stack<Float> stack;
    stack = new Stack<Float>();
    ArrayList<Float> arr = new ArrayList<Float>();

    for (int i = 0; i < 1000; ++i) {
      if ((i * 5) / 2 == 0) {
        float num = (float) Math.random();
        stack.push(num);
        arr.add(num);
      } else {
        if (arr.size() != 0) {
          int size = arr.size() - 1;
          float elementArr = arr.remove(size);
          float elementStack = stack.pop();
          Assert.assertEquals(elementArr, elementStack, 0.0001);
        }
      }
    }
    Assert.assertEquals(arr.size(), stack.amountOfElements());
  }

  @Test
  public void test3() {
    Stack<String> stack = new Stack<String>();
    stack.push("Hello.");
    stack.push("It's me.");
    stack.pop();
    stack.pop();
    try {
      stack.pop();
      Assert.fail("Expected NoSuchElementException");
    } catch (IndexOutOfBoundsException ignored) {
    }
  }

  @Test
  public void test4() {
    Stack<String> stack;
    stack = new Stack<String>();
    ArrayList<String> arr = new ArrayList<String>();
    for (int i = 1; i < 500; ++i) {
      if (i % 2 != 0) {
        stack.push("Today is a bad day. Like always...");
        arr.add("Today is a bad day. Like always...");
      } else {
        stack.push("Nickelback - the best music band ever!!!");
        arr.add("Nickelback - the best music band ever!!!");
      }
    }
    Assert.assertEquals(arr.size(), stack.amountOfElements());
  }

  @Test
  public void test5() {
    Stack<String> stack = new Stack<>();
    stack.push(null);
    String testStr = stack.pop();
    Assert.assertNull(testStr);
    Assert.assertTrue(stack.isEmpty());
  }

  @Test
  public void test6() {
    for (int i = 0; i < 237; ++i) {
      Stack<Integer> stack = new Stack<Integer>();
      ArrayList<Integer> arr = new ArrayList<Integer>();
      stack.push(i);
      arr.add(i);
      Iterator<Integer> iter = stack.iterator();
      Iterator<Integer> iter2 = arr.iterator();
      while (iter.hasNext()) {
        Assert.assertEquals(iter.next(), iter2.next());
      }
    }
  }

  @Test
  public void test7() {
    Stack<Integer> stack = new Stack<Integer>();
    ArrayList<Integer> arr = new ArrayList<Integer>();
    Iterator<Integer> iter = stack.iterator();
    Iterator<Integer> iter2 = arr.iterator();
    try{
    Integer el = iter.next();
    Integer el2 = iter.next();
    } catch (EmptyStackException e){
    }
  }
}
