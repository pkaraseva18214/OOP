package ru.nsu.fit.karaseva.calculator;

public class Plus implements Operations {
  @Override
  public int getNumberOfArguments() {
    return 2;
  }

  @Override
  public Double calculateOperation(Double... args) {
    return args[0]+args[1];
  }
}
