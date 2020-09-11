package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** Class that implements start of a working day. */
public class Start {
  public static void main(String[] args) throws InterruptedException {
    LinkedBlockingQueue<Order> waitingOrders = new LinkedBlockingQueue<>();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<>(9);
    File bakers = new File("src/main/java/ru/nsu/fit/karaseva/pizzeria", args[0]);
    File deliveryWorkers = new File("src/main/java/ru/nsu/fit/karaseva/pizzeria", args[1]);
    Pizzeria pizzeria = new Pizzeria(bakers, deliveryWorkers, itemsInStorage, waitingOrders);
    PizzeriaOverview pizzeriaOverview = pizzeria.start();
  }
}
