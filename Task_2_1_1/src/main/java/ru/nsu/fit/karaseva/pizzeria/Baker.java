package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.annotation.*;

/**
 * Class that represents work of the baker in pizzeria.
 */
class Baker implements Runnable {

  @JsonProperty("id")
  private final int id;

  @JsonProperty("cookingTime")
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

  int getId() {
    return id;
  }

  int getCookingTime() {
    return cookingTime;
  }

  boolean isWaitingForOrder() {
    return waitingForOrder;
  }

  void setStorage(Storage storage) {
    this.storage = storage;
  }

  void setIncomingOrders(IncomingOrders incomingOrders) {
    this.incomingOrders = incomingOrders;
  }

  void setPizzeriaOverview(PizzeriaOverview pizzeriaOverview) {
    this.pizzeriaOverview = pizzeriaOverview;
  }

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
