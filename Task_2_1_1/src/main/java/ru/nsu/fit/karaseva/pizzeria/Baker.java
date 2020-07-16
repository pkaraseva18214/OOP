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

  Baker(BakerConfig bakerConfig){
    this.id = bakerConfig.getId();
    this.cookingTime = bakerConfig.getCookingTime();
    waitingForOrder = false;
  }

  public boolean isWaitingForOrder() {
    return waitingForOrder;
  }

  public int getId(){
    return id;
  }

  public int getCookingTime(){
    return cookingTime;
  }

  /**
   * Sets storage of cooked pizzas.
   *
   * @param itemsInStorage - storage of cooked pizzas.
   */
  void setStorage(ArrayBlockingQueue<Order> itemsInStorage) {
    this.itemsInStorage = itemsInStorage;
  }

  /**
   * Sets incoming orders for bakers.
   *
   * @param waitingOrders
   */
  void setIncomingOrders(LinkedBlockingQueue<Order> waitingOrders) {
    this.waitingOrders = waitingOrders;
  }

  /**
   * Sets overview of the pizzeria.
   *
   * @param pizzeriaOverview
   */
  void setPizzeriaOverview(PizzeriaOverview pizzeriaOverview) {
    this.pizzeriaOverview = pizzeriaOverview;
  }

  /**
   * Sets bakers of the pizzeria.
   *
   * @param bakers
   */
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
        e.printStackTrace();
      }
      ;

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
          e.printStackTrace();
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
        e.printStackTrace();
      }
    }
    pizzeriaOverview.endShiftForBaker();
    System.out.println("Baker #" + id + " finished his work for today.");
  }
}
