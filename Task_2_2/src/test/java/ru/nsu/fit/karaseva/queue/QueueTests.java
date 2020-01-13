package ru.nsu.fit.karaseva.queue;

import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

public class QueueTests {

  @Test
  public void test1() {
    Queue<Integer, Integer> queue = new Queue<>();
    for (int j = 0; j < 100; ++j) {
      queue.add(j, j + 3);
    }
    Assert.assertEquals(100, queue.queueSize());
    int max = queue.extractMax();
    Assert.assertEquals(102, max);
    int min = queue.extractMin();
    Assert.assertEquals(3, min);
  }

  @Test
  public void test2() {
    Queue<Character, String> queue = new Queue<>();
    for (char i = 'A'; i <= 'Z'; ++i) {
      queue.add(i, i + " - in alphabet!");
    }
    Assert.assertEquals(queue.queueSize(), 'Z' - 'A' + 1);
    Assert.assertEquals(queue.extractMax(), "Z - in alphabet!");
    Assert.assertEquals(queue.extractMin(), "A - in alphabet!");
    Assert.assertEquals(queue.queueSize(), 'Z' - 'A' - 1);
  }

  @Test
  public void test3() {
    Queue<Float, Integer> queue = new Queue<>();
    int a = 5;
    for (float i = 0; i < 100; ++i) {
      queue.add((float) (i / 1000), (int) (a * i - 42));
    }
    int max = queue.extractMax();
    int min = queue.extractMin();
    int min2 = queue.extractMin();
    Assert.assertEquals(-42, min);
    Assert.assertEquals(-37, min2);
    Assert.assertEquals(453, max);
    Assert.assertEquals(queue.queueSize(), 97);
  }

  @Test
  public void test4() {
    Queue<Integer, Integer> queue = new Queue<>();
    queue.add(147, null);
    Assert.assertEquals(queue.isEmpty(), false);
    Integer obj = queue.extractMax();
    Assert.assertEquals(queue.isEmpty(), true);
    try {
      queue.add(null, 1);
      Assert.fail();
    } catch (NullPointerException ignored) {
    }
    Assert.assertEquals(queue.isEmpty(), true);
  }

  @Test
  public void test5() {
    Queue<Integer, Integer> queue = new Queue<>();
    for (int i = 0; i < 100; ++i) {
      queue.add(i, i * 2);
    }
    Iterator<Integer> iterator = queue.iterator();
    for (int i = 0; i < 100; ++i) {
      int next = iterator.next();
      Assert.assertEquals(i * 2, next);
    }
    Assert.assertFalse(iterator.hasNext());
  }
}
