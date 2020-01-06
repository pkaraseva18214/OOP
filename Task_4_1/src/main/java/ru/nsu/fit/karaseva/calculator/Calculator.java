package ru.nsu.fit.karaseva.calculator;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

/** Class that implements calculator. */
public class Calculator {
  private boolean correctness = true;
  private String str;
  Deque<Double> numbers = new ArrayDeque<>();
  Stack<String> operations = new Stack<>();

  boolean prepareToCalculate(String in) {
    Arrays.asList(in.split(" ")).stream()
        .forEach(
            number -> {
              if (number.charAt(0) >= 48 && number.charAt(0) <= 57) {
                try {
                  numbers.add(Double.parseDouble(number));
                } catch (NumberFormatException ignore) {
                  correctness = false;
                }
              } else operations.push(number);
            });
    return correctness;
  }

  /**
   * Method that parses input expression and calculates it.
   *
   * @param exp input expression.
   * @return result of calculations.
   */
  public Double calculator(String exp) {
    str = exp;
    Calculations calc = new Calculations();
    Double res = calc.Calculations(exp);
    return res;
  }
}