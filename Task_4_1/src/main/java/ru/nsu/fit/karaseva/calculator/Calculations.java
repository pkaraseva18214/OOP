package ru.nsu.fit.karaseva.calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/** Class that implement calculations for calculator. */
public class Calculations {
  private Double[] args;
  private Double res;
  private Calculator calc = new Calculator();
  private OperationFactory f;

  /**
   * Calculate expression.
   * @param exp input expression.
   * @return result of calculations, otherwise (if something wrong) returns null.
   */
  public Double calculations(String exp, OperationFactory factory) {
    this.f = factory;
    if (!calc.prepareToCalculate(exp)){
      return null;
    }
    if (calc.operations.empty() || calc.numbers.isEmpty()) {
      return null;
    }
    args = new Double[2];
    while (!calc.operations.empty()) {
      String operation = getOperation();
      //Factory op = new Factory();
      if (!f.isSupported(operation)) {
        return null;
      }
      Operations usedOperation = f.getOperation(operation);
      for (int i = 0; i < usedOperation.getNumberOfArguments(); i++) {
        args[i] = getNumbers();
      }
      calc.numbers.addFirst(usedOperation.calculateOperation(args[0], args[1]));
    }
    return res = calc.numbers.remove();
  }

  private Double getNumbers() {
    return calc.numbers.removeFirst();
  }

  private String getOperation() {
    return calc.operations.pop();
  }

}

