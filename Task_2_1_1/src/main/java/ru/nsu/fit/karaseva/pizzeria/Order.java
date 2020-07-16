package ru.nsu.fit.karaseva.pizzeria;

/** Class that represents order in pizzeria. */
public class Order {
  private final int id;

  public Order(int id) {
    this.id = id;
  }

  int getId() {
    return id;
  }
}
