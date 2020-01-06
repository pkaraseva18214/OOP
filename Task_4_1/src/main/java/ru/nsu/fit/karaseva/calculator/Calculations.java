package ru.nsu.fit.karaseva.calculator;

/** Class that implement calculations for calculator. */
public class Calculations extends Calculator {
  private Double[] args;
  private Double res;

  /**
   * Calculate expression.
   * @param exp input expression.
   * @return result of calculations, otherwise (if something wrong) returns null.
   */
  public Double Calculations(String exp) {
    if (!prepareToCalculate(exp)){
      return null;
    }
    if (operations.empty() || numbers.isEmpty()) {
      return null;
    }
    args = new Double[2];
    while (!operations.empty()) {
      String operation = getOperation();
      Factory op = new Factory();
      if (!op.isSupported(operation)) {
        return null;
      }
      Operations usedOperation = op.getOperation(operation);
      for (int i = 0; i < usedOperation.getNumberOfArguments(); i++) {
        args[i] = getNumbers();
      }
      numbers.addFirst(usedOperation.calculateOperation(args[0], args[1]));
    }
    return res = numbers.remove();
  }

  private Double getNumbers() {
    return numbers.removeFirst();
  }

  private String getOperation() {
    return operations.pop();
  }
}

