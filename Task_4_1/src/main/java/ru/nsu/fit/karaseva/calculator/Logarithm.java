package ru.nsu.fit.karaseva.calculator;

public class Logarithm implements Operations {

  @Override
  public int getNumberOfArguments() {
    return 1;
  }

  @Override
  public Double calculateOperation(Double... args) {
    return Math.log(args[0]);
  }
}
