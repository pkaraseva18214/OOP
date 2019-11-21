package ru.nsu.fit.karaseva.stack;

/**
 * Class that implements stack element.
 * @param <T> type of the elements in the stack.
 */
class StackElement<T> {

    private T element;
    private StackElement<T> nextElement;
    private StackElement<T> previousElement;

    /**
     * Method that creates new stack element.
     * @param newElement new stack element.
     * @param previous previous element in the stack.
     */
    public StackElement(T newElement, StackElement<T> previous) {
        element = newElement;
        previousElement = previous;
        if (previous != null) {
            previous.nextElement = this;
        }
    }

    void setNextElement(StackElement<T> next) {
        nextElement = next;
        if (next != null){
            next.previousElement = this;
        }
    }

    /**
     * Method for getting element of the stack.
     * @return element of the stack.
     */
    public T getElement() {
        return element;
    }

    /**
     * Method for getting previous element in the stack.
     * @return previous element.
     */
    public StackElement<T> getPreviousElement() {
        return previousElement;
    }

    /**
     * Method for getting next element in the stack.
     * @return next element.
     */
    public StackElement<T> getNextElement() {
        return nextElement;
    }
}
