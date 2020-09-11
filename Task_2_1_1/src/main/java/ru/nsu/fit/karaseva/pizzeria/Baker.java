package ru.nsu.fit.karaseva.pizzeria;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** Class that represents work of the baker in pizzeria. */
public class Baker implements Runnable {
  private final int id;
  private final int cookingTime;
  private boolean waitingForOrder;

  private LinkedBlockingQueue<Order> waitingOrders;
  private PizzeriaOverview pizzeriaOverview;
  private Bakers bakers;
  private ArrayBlockingQueue<Order> itemsInStorage;

  Baker(
      BakerConfig bakerConfig,
      LinkedBlockingQueue<Order> waitingOrders,
      PizzeriaOverview pizzeriaOverview,
      ArrayBlockingQueue<Order> itemsInStorage) {
    if (bakerConfig != null) {
      this.id = bakerConfig.getId();
      this.cookingTime = bakerConfig.getCookingTime();
      waitingForOrder = false;
      this.itemsInStorage = itemsInStorage;
      this.waitingOrders = waitingOrders;
      this.pizzeriaOverview = pizzeriaOverview;
    } else throw new NullPointerException();
  }

  public boolean isWaitingForOrder() {
    return waitingForOrder;
  }

  public int getId() {
    return id;
  }

  public int getCookingTime() {
    return cookingTime;
  }

  void setBakers(Bakers bakers) {
    this.bakers = bakers;
  }

  @Override
  public void run() {
    while (!pizzeriaOverview.isRestaurantClosed() || !waitingOrders.isEmpty()) {
      Order currentOrder = null;
      try {
        currentOrder = waitingOrders.take();
      } catch (InterruptedException e) {
        assert false;
      }
      try {
        this.waitingForOrder = true;
        bakers.lock.lock();
        if (pizzeriaOverview.isRestaurantClosed() && waitingOrders.isEmpty()) {
          break;
        }
        this.waitingForOrder = false;
        try {
          Thread.sleep(cookingTime);
        } catch (InterruptedException e) {
          assert false;
        }
      } finally {
        bakers.lock.unlock();
      }
      System.out.println(
          "Baker #" + id + " is making a pizza. Order #" + currentOrder.getId() + ".");
      System.out.println(
          "Baker #" + id + " finished making a pizza. Order #" + currentOrder.getId() + ".");
      System.out.println("Baker #" + id + " put pizza in the storage.");
      try {
        itemsInStorage.put(currentOrder);
      } catch (InterruptedException e) {
        assert false;
      }
    }
    pizzeriaOverview.endShiftForBaker();
    System.out.println("Baker #" + id + " finished his work for today.");
  }
}
