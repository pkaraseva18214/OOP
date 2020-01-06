package ru.nsu.fit.karaseva.calculator;

public class Factory implements OperationFactory {
  /** Method that checks if operation is supported in calculator.
   * @param operation  name of the operation.
   * @return true if operation is supported, false - otherwise.
   */
  public boolean isSupported(String operation) {
    return (operation.equals("sin")
        || operation.equals("cos")
        || operation.equals("-")
        || operation.equals("/")
        || operation.equals("*")
        || operation.equals("+")
        || operation.equals("sqrt")
        || operation.equals("log")
        || operation.equals("pow"));
  }

  /**
   * Method that returns object of one of the operation classes.
   *
   * @param operation name of the operation.
   * @return new object of operation class, otherwise return null.
   */
  public Operations getOperation(String operation) {
    switch (operation) {
      case "+":
        return new Plus();
      case "-":
        return new Minus();
      case "/":
        return new Division();
      case "*":
        return new Multiplication();
      case "sin":
        return new Sin();
      case "cos":
        return new Cos();
      case "pow":
        return new Power();
      case "sqrt":
        return new Squared();
      case "log":
        return new Logarithm();
      default:
        return null;
    }
  }
}
