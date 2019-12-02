package ru.nsu.fit.karaseva.calculator;

import java.util.*;

/** Class that implement calculations for calculator. */
public class Calculations extends Calculator {

  /**
   * Method for calculating input expression.
   *
   * @param exp expression that needed to be calculated.
   * @return result of calculation.
   */
  public Double Calculations(String exp) {
    prepareToCalculate(exp);
    if (!operations.empty()) {
      while (!operations.empty()) {
        String operation = getOperation();
        if (operation.length() == 1 || operation.equals("pow")) {
          try {
            Double n1 = getNumbers();
            Double n2 = getNumbers();
            if (n1 != null || n2 != null) {
              calculateValue(n1, n2, operation);
            }
          } catch (NoSuchElementException ex) {
            System.out.println("No numbers were entered.");
          }
        } else {
          try {
            Double n = getNumbers();
            if (n != null) calculateValue2(n, operation);
          } catch (NoSuchElementException ex) {
            System.out.println("No numbers were entered.");
          }
        }
      }
    } else {
      throw new NoSuchElementException("No arithmetic operations entered.");
    }
    Double res = numbers.remove();
    return res;
  }

  private void calculateValue(Double n1, Double n2, String op) {
    try {
      switch (op) {
        case ("+"):
          {
            numbers.addFirst(n1 + n2);
            break;
          }
        case ("-"):
          {
            numbers.addFirst(n1 - n2);
            break;
          }
        case ("*"):
          {
            numbers.addFirst(n1 * n2);
            break;
          }
        case ("/"):
          {
            numbers.addFirst(n1 / n2);
            break;
          }
        case ("pow"):
          {
            numbers.addFirst(Math.pow(n1, n2));
            break;
          }
      }
    } catch (IllegalArgumentException ex) {
      System.out.println("Invalid arithmetical operation entered.");
    }
  }

  private void calculateValue2(Double n, String op) {
    try {
      switch (op) {
        case ("sin"):
          {
            numbers.addFirst(Math.sin(n));
            break;
          }
        case ("cos"):
          {
            numbers.addFirst(Math.cos(n));
            break;
          }
        case ("log"):
          {
            numbers.addFirst(Math.log(n));
            break;
          }
        case ("sqrt"):
          {
            numbers.addFirst(Math.sqrt(n));
            break;
          }
      }
    } catch (IllegalArgumentException ex) {
      System.out.println("Invalid arithmetical operation entered.");
    }
  }

  private Double getNumbers() {
    return numbers.poll();
  }

  private String getOperation() {
    return operations.pop();
  }
}
