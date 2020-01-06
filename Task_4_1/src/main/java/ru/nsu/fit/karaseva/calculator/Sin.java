package ru.nsu.fit.karaseva.calculator;

public class Sin implements Operations {

  @Override
  public int getNumberOfArguments() {
    return 1;
  }

  @Override
  public Double calculateOperation(Double... args) {
    return Math.sin(args[0]);
  }
}
