package ru.nsu.fit.karaseva.pizzeria;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that represents list of the incoming orders.
 */
class IncomingOrders {
  private final LinkedBlockingQueue<Order> waitingOrders;

  IncomingOrders() {
    waitingOrders = new LinkedBlockingQueue<>();
  }

  boolean areThereAnyOrders() {
    return waitingOrders.isEmpty();
  }

  void order(Order order) {
    try {
      waitingOrders.put(order);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  Order takeOrder() {
    try {
      return waitingOrders.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }
}