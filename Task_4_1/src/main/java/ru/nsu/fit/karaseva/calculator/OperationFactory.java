package ru.nsu.fit.karaseva.calculator;

public interface OperationFactory {
  /**
   * Method that checks if operation is supported in calculator.
   *
   * @param operation - name of checked operation.
   * @return true if operation supported.
   * @throws NullPointerException is you put null as operation
   */
  boolean isSupported(String operation) throws NullPointerException;
  /**
   * Method that returns object of one of the operation classes.
   *
   * @param operation - name of return operation.
   * @return Operation if it supported, else return null
   * @throws NullPointerException is you put null as operationName
   */
  Operations getOperation(String operation);
}
