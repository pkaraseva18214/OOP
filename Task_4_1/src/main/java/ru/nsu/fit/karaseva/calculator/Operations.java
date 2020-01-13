package ru.nsu.fit.karaseva.calculator;

public interface Operations {
  /**
   * Method for getting number of arguments in operations.
   * @return number of arguments
   */
  public abstract int getNumberOfArguments();

  /**
   * Method that calculates the value of operation.
   * @param args
   * @return
   */
  public abstract Double calculateOperation(Double ... args);
}
