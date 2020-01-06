package ru.nsu.fit.karaseva.calculator;

public class Power implements Operations {

  @Override
  public int getNumberOfArguments() {
    return 2;
  }

  @Override
  public Double calculateOperation(Double... args) {
    return Math.pow(args[0], args[1]);
  }
}
