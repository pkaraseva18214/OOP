package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents work of the delivery worker in pizzeria.
 */
class DeliveryWorker implements Runnable {
  private static final int WAITING_TIME = 1000;

  @JsonProperty("id")
  private final int id;

  @JsonProperty("capacity")
  private final int capacity;

  @JsonProperty("deliveryTime")
  private final int deliveryTime;

  private final List<Order> bag;
  private Storage storage;
  private PizzeriaOverview pizzeriaOverview;
  private DeliveryWorkers deliveryWorkers;

  DeliveryWorker(
      @JsonProperty("id") int id,
      @JsonProperty("deliveryTime") int deliveryTime,
      @JsonProperty("howManyPizzasCanCarry") int capacity) {
    this.id = id;
    this.deliveryTime = deliveryTime;
    this.capacity = capacity;
    this.bag = new ArrayList<>();
  }

  int getId() {
    return id;
  }

  int getDeliveryTime() {
    return deliveryTime;
  }

  int getNumOfPizzasCanCarry() {
    return capacity;
  }

  void setStorage(Storage storage) {
    this.storage = storage;
  }

  void setPizzaRestaurantHeadquarters(PizzeriaOverview pizzeriaOverview) {
    this.pizzeriaOverview = pizzeriaOverview;
  }

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
                        + " is ready to deliver! Another pizza would fit in the bag.");
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
          pizzeriaOverview.completeOrder();
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
