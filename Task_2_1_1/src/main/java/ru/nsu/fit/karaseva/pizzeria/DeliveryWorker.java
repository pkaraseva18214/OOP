package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents work of the delivery worker in pizzeria.
 */
public class DeliveryWorker implements Runnable {
  private static int WAITING_TIME = 1000;
  private final int id;
  private final int capacity;
  private final int deliveryTime;

  private final List<Order> bag;
  private Storage storage;
  private PizzeriaOverview pizzeriaOverview;
  private DeliveryWorkers deliveryWorkers;

  DeliveryWorker(
      @JsonProperty("id") int id,
      @JsonProperty("deliveryTime") int deliveryTime,
      @JsonProperty("capacity") int capacity) {
    this.id = id;
    this.deliveryTime = deliveryTime;
    this.capacity = capacity;
    this.bag = new ArrayList<>();
  }

  /**
   * Returns id of the delivery worker.
   * @return
   */
  int getId() {
    return id;
  }

  /**
   * Returns delivery time of the delivery worker.
   * @return
   */
  int getDeliveryTime() {
    return deliveryTime;
  }

  /**
   * Returns capacity of the delivery worker's bag.
   * @return
   */
  int getCapacity() {
    return capacity;
  }

  /**
   * Set storage of cooked pizzas.
   * @param storage
   */
  void setStorage(Storage storage) {
    this.storage = storage;
  }

  /**
   * Sets pizzeria overview.
   * @param pizzeriaOverview
   */
  void setPizzeriaOverview(PizzeriaOverview pizzeriaOverview) {
    this.pizzeriaOverview = pizzeriaOverview;
  }

  /**
   * Sets delivery workers.
   * @param deliveryWorkers
   */
  void setDeliveryWorkers(DeliveryWorkers deliveryWorkers) {
    this.deliveryWorkers = deliveryWorkers;
  }

  @Override
  public void run() {
    while (!pizzeriaOverview.isRestaurantClosed()
        || !(storage.numOfItemsInStorage() == 0
        && pizzeriaOverview.areAllBakersFinishedWork())) {

      boolean isClosed = false;
      deliveryWorkers.lock.lock();
      try {
        if (pizzeriaOverview.isRestaurantClosed()
            && storage.numOfItemsInStorage() == 0
            && pizzeriaOverview.areAllBakersFinishedWork()) {
          break;
        }
        for (int i = 0; i < capacity; i++) {
          Order order = null;
          if (bag.size() != 0) {
            try {
              order = storage.pickItemForDelivery(WAITING_TIME);
              if (order == null) {
                System.out.println(
                    "Delivery Worker #"
                        + id
                        + " is ready to deliver. Another pizza would fit in the bag.");
                break;
              }
              bag.add(order);
              System.out.println(
                  "Delivery Worker #"
                      + id
                      + " picked up the Order #"
                      + order.getId()
                      + ". He has "
                      + bag.size()
                      + " pizza(s) in the bag.");
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          } else {
            while (bag.size() == 0) {
              if (pizzeriaOverview.isRestaurantClosed()
                  && storage.numOfItemsInStorage() == 0
                  && pizzeriaOverview.areAllBakersFinishedWork()) {

                System.out.println("We are closed!");

                isClosed = true;
                break;
              }
              order = storage.pickItemForDelivery(WAITING_TIME);
              if (order != null) {
                bag.add(order);
              }
            }
            if (isClosed) {
              break;
            }
            System.out.println(
                "Delivery Worker #"
                    + id
                    + " picked up the Order #"
                    + Objects.requireNonNull(order).getId()
                    + ". He has "
                    + bag.size()
                    + " pizza(s) in the bag.");
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        deliveryWorkers.lock.unlock();
      }
      if (isClosed) {
        break;
      }
      try {
        for (Order order : bag) {
          Thread.sleep(deliveryTime);
          System.out.println("Delivery Worker #" + id + " delivered your pizza.");
        }
        System.out.println("Delivery Worker #" + id + " delivered all the orders.");
        Thread.sleep(deliveryTime);
        bag.clear();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    pizzeriaOverview.endShiftForDeliveryWorker();
    System.out.println("Delivery Worker #" + id + " is done for today.");
  }
}
