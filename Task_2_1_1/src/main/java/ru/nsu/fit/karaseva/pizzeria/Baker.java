package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.annotation.*;

/**
 * Class that represents work of the baker in pizzeria.
 */
public class Baker implements Runnable {
  private final int id;
  private final int cookingTime;
  private boolean waitingForOrder;

  private Storage storage;
  private IncomingOrders incomingOrders;
  private PizzeriaOverview pizzeriaOverview;
  private Bakers bakers;

  Baker(@JsonProperty("id") int id, @JsonProperty("cookingTime") int cookingTime) {
    this.id = id;
    this.cookingTime = cookingTime;
    waitingForOrder = false;
  }

  /**
   * Returns id of the baker.
   * @return id of the baker
   */
  int getId() {
    return id;
  }

  /**
   * Returns cooking time of the baker.
   * @return cooking time
   */
  int getCookingTime() {
    return cookingTime;
  }

  /**
   * Returns waiting status of the baker.
   * @return true - if baker is waiting for order, otherwise false.
   */
  boolean isWaitingForOrder() {
    return waitingForOrder;
  }

  /**
   * Sets storage of cooked pizzas.
   * @param storage - storage of cooked pizzas.
   */
  void setStorage(Storage storage) {
    this.storage = storage;
  }

  /**
   * Sets incoming orders for bakers.
   * @param incomingOrders
   */
  void setIncomingOrders(IncomingOrders incomingOrders) {
    this.incomingOrders = incomingOrders;
  }

  /**
   * Sets overview of the pizzeria.
   * @param pizzeriaOverview
   */
  void setPizzeriaOverview(PizzeriaOverview pizzeriaOverview) {
    this.pizzeriaOverview = pizzeriaOverview;
  }

  /**
   * Sets bakers of the pizzeria.
   * @param bakers
   */
  void setBakers(Bakers bakers) {
    this.bakers = bakers;
  }

  @Override
  public void run() {

    while (!pizzeriaOverview.isRestaurantClosed()
        || !incomingOrders.areThereAnyOrders()) {

      Order currentOrder;

      try {
        this.waitingForOrder = true;
        bakers.lock.lock();
        if (pizzeriaOverview.isRestaurantClosed() && incomingOrders.areThereAnyOrders()) {
          break;
        }
        currentOrder = incomingOrders.takeOrder();
        this.waitingForOrder = false;
        try{
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
          "Baker #"
              + id
              + " finished making a pizza. Order #"
              + currentOrder.getId()
              + ".");
      System.out.println("Baker #" + id + " put pizza in the storage.");
      storage.putItemAwayInStorage(currentOrder);
    }
    pizzeriaOverview.endShiftForBaker();
    System.out.println("Baker #" + id + " finished his work for today.");
  }

}
