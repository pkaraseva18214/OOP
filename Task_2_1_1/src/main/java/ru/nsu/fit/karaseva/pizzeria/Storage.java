package ru.nsu.fit.karaseva.pizzeria;

import java.util.concurrent.TimeUnit;

/**
 * Class that represent storage of pizzas (baker made pizza,
 * put here and delivery worker get pizza..
 */
class Storage {
  private static ArrayBlockingQueue<Order> itemsInStorage;

  Storage(int capacity) {
    itemsInStorage = new ArrayBlockingQueue<>(capacity, true);
  }

  int numOfItemsInStorage() {
    return itemsInStorage.size();
  }

  void putItemAwayInStorage(Order order) {
    try {
      itemsInStorage.put(order);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  Order pickItemForDelivery(int milliseconds) throws InterruptedException {
    return itemsInStorage.poll(milliseconds, TimeUnit.MILLISECONDS);
  }
}
