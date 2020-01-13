package ru.nsu.fit.karaseva.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that implements queue.
 *
 * @param <I> Type of the index.
 * @param <T> Type of the object (value).
 */
public class Queue<I extends Comparable<I>, T> {

  private int queueSize = 0;
  private QueueElement<T, I> beginningOfQueue = null;
  private QueueElement<T, I> endOfQueue = null;

  /**
   * Method that adds new element in the queue.
   *
   * @param index index of the new element.
   * @param value value of the new element.
   * @throws NullPointerException
   */
  public void add(I index, T value) throws NullPointerException {
    if (index == null) {
      throw new NullPointerException();
    }
    if (beginningOfQueue == null) {
      beginningOfQueue = new QueueElement<>(index, value, null, null);
      endOfQueue = beginningOfQueue;
      queueSize++;
      return;
    }
    if (index.compareTo(beginningOfQueue.getIndex()) <= 0) {
      beginningOfQueue = new QueueElement<>(index, value, null, beginningOfQueue);
      ++queueSize;
      return;
    }
    if (index.compareTo(endOfQueue.getIndex()) > 0) {
      endOfQueue = new QueueElement<>(index, value, endOfQueue, null);
      ++queueSize;
      return;
    }
    QueueElement<T, I> currentElement = beginningOfQueue;
    for (int i = 0; i < queueSize - 1; ++i) {
      I currentIndex = currentElement.getIndex();
      I nextIndex = currentElement.getNextElement().getIndex();
      if (currentIndex.compareTo(index) <= 0 && nextIndex.compareTo(index) >= 0) {
        QueueElement<T, I> nextQueueElement = currentElement.getNextElement();
        new QueueElement<>(index, value, currentElement, nextQueueElement);
        ++queueSize;
        break;
      }
      currentElement = currentElement.getNextElement();
    }
  }

  /**
   * Method that deletes max element in the queue.
   *
   * @return value of the max element.
   * @throws NoSuchElementException
   */
  public T extractMax() throws NoSuchElementException {
    queueSize--;
    if (endOfQueue != null && endOfQueue == beginningOfQueue) {
      T returnedValue = endOfQueue.getValue();
      beginningOfQueue = null;
      endOfQueue = null;
      return returnedValue;
    }
    if (endOfQueue != null) {
      T returnedValue = endOfQueue.getValue();
      endOfQueue = endOfQueue.getPreviousElement();
      endOfQueue.setNextElement(null);
      return returnedValue;
    } else {
      throw new NoSuchElementException("Queue is empty");
    }
  }

  /**
   * Method that deletes min element in the queue.
   *
   * @return value of the min element.
   * @throws NoSuchElementException
   */
  public T extractMin() throws NoSuchElementException {
    queueSize--;
    if (endOfQueue != null && endOfQueue == beginningOfQueue) {
      T returnedValue = endOfQueue.getValue();
      endOfQueue = null;
      beginningOfQueue = null;
      return returnedValue;
    }
    if (beginningOfQueue != null) {
      T returnedValue = beginningOfQueue.getValue();
      beginningOfQueue = beginningOfQueue.getNextElement();
      endOfQueue.setPreviousElement(null);
      return returnedValue;
    } else {
      throw new NoSuchElementException("Queue is empty");
    }
  }

  /** Implementation of Iterator for class Stack. */
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private QueueElement<T, I> currentElement = beginningOfQueue;

      /**
       * Checks is there next element.
       *
       * @return true - if next element exists, else returns false.
       */
      public boolean hasNext() {
        return currentElement != null;
      }

      /**
       * Method that returns next element.
       *
       * @return next element if it exists.
       * @throws IndexOutOfBoundsException
       */
      public T next() throws IndexOutOfBoundsException {
        if (queueSize == 0) {
          throw new IndexOutOfBoundsException("Queue is empty.");
        }
        if (currentElement == null) {
          throw new NoSuchElementException("End of queue.");
        }
        T result = currentElement.getValue();
        currentElement = currentElement.getNextElement();
        return result;
      }
    };
  }

  /**
   * Check if queue is empty.
   *
   * @return true - if empty, false - not empty.
   */
  public boolean isEmpty() {
    if (queueSize <= 0) {
      return true;
    }
    return false;
  }

  /**
   * Getting size of the queue.
   *
   * @return size od the queue.
   */
  public int queueSize() {
    return queueSize;
  }
}
