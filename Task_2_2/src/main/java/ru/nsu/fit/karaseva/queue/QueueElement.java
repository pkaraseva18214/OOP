package ru.nsu.fit.karaseva.queue;

/**
 * Class that implements element of the queue.
 *
 * @param <T> Type of the object (value).
 * @param <I> Type of the index.
 */
public class QueueElement<T, I> {

  private T value;
  private I index;
  private QueueElement<T, I> nextElement;
  private QueueElement<T, I> previousElement;

  /**
   * Constructor of elements in the queue.
   *
   * @param indexElement index of the new element.
   * @param newValue value of the new element.
   * @param previous previous element in the queue.
   * @param next next element in the queue.
   */
  public QueueElement(
      I indexElement, T newValue, QueueElement<T, I> previous, QueueElement<T, I> next) {
    value = newValue;
    index = indexElement;
    previousElement = previous;
    nextElement = next;
    if (previous != null) {
      previous.setNextElement(this);
    }
  }

  /**
   * Method for getting value of the element in the queue.
   *
   * @return value of the element.
   */
  public T getValue() {
    return value;
  }

  /**
   * Method for getting index of the element in the queue.
   *
   * @return index of the element.
   */
  public I getIndex() {
    return index;
  }

  void setNextElement(QueueElement<T, I> next) {
    nextElement = next;
    if (next != null) {
      next.previousElement = this;
    }
  }

  void setPreviousElement(QueueElement<T, I> previous) {
    previousElement = previous;
    if (previous != null) previous.nextElement = this;
  }

  QueueElement<T, I> getPreviousElement() {
    return previousElement;
  }

  QueueElement<T, I> getNextElement() {
    return nextElement;
  }
}
