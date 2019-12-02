package ru.nsu.fit.karaseva.calculator;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner console = new Scanner(System.in);
    System.out.println("This program evaluates prefix expressions");
    System.out.println("for operators +, -, *, /, sin, cos, sqrt, log, pow.");
    System.out.println("Please, enter your expression: ");
    String exp = console.nextLine();
    Calculator calc = new Calculator();
    Double res = calc.calculator(exp);
    System.out.print(" = " + res);
  }
}
