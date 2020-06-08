package ru.nsu.fit.karaseva.pizzeria;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/*
class ArrayBlockingQueue<E> {

  private final ReentrantLock lock;
  private final Object[] items;
  private final Condition notEmpty;
  private final Condition notFull;
  private int takeIndex;
  private int putIndex;
  private int count;

  ArrayBlockingQueue(int capacity, boolean fair) {
    if (capacity <= 0) {
      throw new IllegalArgumentException();
    }
    this.items = new Object[capacity];
    lock = new ReentrantLock(fair);
    notEmpty = lock.newCondition();
    notFull = lock.newCondition();
  }

  private static void checkNotNull(Object v) {
    if (v == null) {
      throw new NullPointerException();
    }
  }

  private static <E> E cast(Object item) {
    return (E) item;
  }

  void put(E e) throws InterruptedException {
    checkNotNull(e);
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
      while (count == items.length) {
        notFull.await();
      }
      insert(e);
    } finally {
      lock.unlock();
    }
  }

  E poll(long timeout, TimeUnit unit) throws InterruptedException {
    long nanoSeconds = unit.toNanos(timeout);
    final ReentrantLock lock = this.lock;
    lock.lockInterruptibly();
    try {
      while (count == 0) {
        if (nanoSeconds <= 0) {
          return null;
        }
        nanoSeconds = notEmpty.awaitNanos(nanoSeconds);
      }
      return extract();
    } finally {
      lock.unlock();
    }
  }

  private void insert(E x) {
    items[putIndex] = x;
    putIndex = inc(putIndex);
    count++;
    notEmpty.signal();
  }

  private int inc(int i) {
    return (++i == items.length) ? 0 : i;
  }

  private E extract() {
    final Object[] items = this.items;
    E x = ArrayBlockingQueue.cast(items[takeIndex]);
    items[takeIndex] = null;
    takeIndex = inc(takeIndex);
    count--;
    notFull.signal();
    return x;
  }

  int size() {
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
      return count;
    } finally {
      lock.unlock();
    }
  }
}
*/