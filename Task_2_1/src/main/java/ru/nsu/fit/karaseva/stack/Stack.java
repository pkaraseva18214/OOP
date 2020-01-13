package ru.nsu.fit.karaseva.stack;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that implements the work of the stack.
 *
 * @param <T> type of the elements.
 */
public class Stack<T> {
  private StackElement<T> positionStackElement = null;
  private StackElement<T> head = null;
  private int position = 0;

  /**
   * Method that pushes the element to the stack.
   *
   * @param element element that we need to put in the stack.
   */
  public void push(T element) {
    position++;
    positionStackElement = new StackElement<>(element, positionStackElement);
    head = positionStackElement;
  }

  /**
   * Method that gets element from the stack.
   *
   * @return element of the stack if it exists, otherwise throws exception.
   * @throws IndexOutOfBoundsException throws this exception if the stack is empty.
   */
  public T pop() throws IndexOutOfBoundsException {
    if (position == 0) {
      throw new IndexOutOfBoundsException();
    } else {
      T element = positionStackElement.getElement();
      positionStackElement = positionStackElement.getPreviousElement();
      if (positionStackElement != null) {
        positionStackElement.setNextElement(null);
      }
      position--;
      if (position == 0) {
        head = null;
      }
      return element;
    }
  }

  /**
   * Implementation of the Iterator for the stack.
   *
   * @return next element of the stack.
   */
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private StackElement<T> current = head;

      public boolean hasNext() {
        return current != null;
      }

      public T next() throws NoSuchElementException {
        if (position == 0){
          throw new EmptyStackException();
        }
        T result = current.getElement();
        if (!hasNext()) {
          throw new NoSuchElementException("End of the stack.");
        }
        current = current.getNextElement();
        return result;
      }
    };
  }

  /**
   * Method that gets amount of elements in the stack.
   *
   * @return amount of elements in the stack.
   */
  public int amountOfElements() {
    return position;
  }

  /**
   * Check if the stack is empty.
   *
   * @return 1 - if the stack is empty, otherwise returns 0.
   */
  public boolean isEmpty() {
    return position == 0;
  }
}
