package ru.nsu.fit.karaseva.calculator;

public class Squared implements Operations {

  @Override
  public int getNumberOfArguments() {
    return 1;
  }

  @Override
  public Double calculateOperation(Double... args) {
    return Math.sqrt(args[0]);
  }
}
