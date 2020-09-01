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

  Baker(BakerConfig bakerConfig) {
    if (bakerConfig != null) {
      this.id = bakerConfig.getId();
      this.cookingTime = bakerConfig.getCookingTime();
      waitingForOrder = false;
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

  /**
   * Sets storage, waiting orders, pizzeria overview and bakers.
   *
   * @param itemsInStorage storage
   * @param waitingOrders waiting orders
   * @param pizzeriaOverview pizzeria overview
   * @param bakers bakers
   */
  public void setAdditionalParameters(
      ArrayBlockingQueue<Order> itemsInStorage,
      LinkedBlockingQueue<Order> waitingOrders,
      PizzeriaOverview pizzeriaOverview,
      Bakers bakers) {
    this.itemsInStorage = itemsInStorage;
    this.waitingOrders = waitingOrders;
    this.pizzeriaOverview = pizzeriaOverview;
    this.bakers = bakers;
  }

  @Override
  public void run() {

    while (!pizzeriaOverview.isRestaurantClosed() || !waitingOrders.isEmpty()) {

      Order currentOrder = null;
      try {
        currentOrder = waitingOrders.take();
      } catch (InterruptedException e) {
        System.out.println(e + "caused by " + e.getCause());
        System.exit(1);
      }
      ;

      this.waitingForOrder = true;
      if (pizzeriaOverview.isRestaurantClosed() && waitingOrders.isEmpty()) {
        break;
      }

      this.waitingForOrder = false;
      try {
        Thread.sleep(cookingTime);
      } catch (InterruptedException e) {
        System.out.println(e + "caused by " + e.getCause());
        System.exit(1);
      }

      System.out.println(
          "Baker #" + id + " is making a pizza. Order #" + currentOrder.getId() + ".");

      System.out.println(
          "Baker #" + id + " finished making a pizza. Order #" + currentOrder.getId() + ".");
      System.out.println("Baker #" + id + " put pizza in the storage.");
      try {
        itemsInStorage.put(currentOrder);
      } catch (InterruptedException e) {
        System.out.println(e + "caused by " + e.getCause());
        System.exit(1);
      }
    }
    pizzeriaOverview.endShiftForBaker();
    System.out.println("Baker #" + id + " finished his work for today.");
  }
}
