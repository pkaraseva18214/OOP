package ru.nsu.fit.karaseva.pizzeria;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Class that represent storage of pizzas (baker made pizza,
 * put here and delivery worker get pizza.)
 */
class Storage {
  private static ArrayBlockingQueue<Order> itemsInStorage;

  Storage(int capacity) {
    itemsInStorage = new ArrayBlockingQueue<>(capacity, true);
  }

  /**
   * Returns number of pizzas in storage.
   * @return
   */
  int numOfItemsInStorage() {
    return itemsInStorage.size();
  }

  /**
   * Puts pizza in storage.
   * @param order
   */
  void putItemAwayInStorage(Order order) {
    try {
      itemsInStorage.put(order);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Removes pizzas from storage.
   * @param milliseconds
   * @return
   * @throws InterruptedException
   */
  Order pickItemForDelivery(int milliseconds) throws InterruptedException {
    return itemsInStorage.poll(milliseconds, TimeUnit.MILLISECONDS);
  }
}
