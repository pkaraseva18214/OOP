package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/** Class that represents work of the delivery worker in pizzeria. */
public class DeliveryWorker implements Runnable {
  private static final int WAITING_TIME = 1000;
  private final int id;
  private final int capacity;
  private final int deliveryTime;

  private final List<Order> bag;
  private ArrayBlockingQueue<Order> itemsInStorage;
  private PizzeriaOverview pizzeriaOverview;
  private DeliveryWorkers deliveryWorkers;

  public DeliveryWorker(DeliveryWorkerConfig deliveryWorkerConfig) {
    this.id = deliveryWorkerConfig.getId();
    this.deliveryTime = deliveryWorkerConfig.getDeliveryTime();
    this.capacity = deliveryWorkerConfig.getCapacity();
    this.bag = new ArrayList<>();
  }

  /**
   * Returns id of the delivery worker.
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Returns delivery time of the delivery worker.
   *
   * @return delivery time
   */
  public int getDeliveryTime() {
    return deliveryTime;
  }

  /**
   * Returns capacity of the delivery worker's bag.
   *
   * @return capacity
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * Set storage of cooked pizzas.
   *
   * @param itemsInStorage storage
   */
  void setStorage(ArrayBlockingQueue<Order> itemsInStorage) {
    this.itemsInStorage = itemsInStorage;
  }

  /**
   * Sets pizzeria overview.
   *
   * @param pizzeriaOverview pizzeria overview
   */
  void setPizzeriaOverview(PizzeriaOverview pizzeriaOverview) {
    this.pizzeriaOverview = pizzeriaOverview;
  }

  /**
   * Sets delivery workers.
   *
   * @param deliveryWorkers delivery workers
   */
  void setDeliveryWorkers(DeliveryWorkers deliveryWorkers) {
    this.deliveryWorkers = deliveryWorkers;
  }

  @Override
  public void run() {
    while (!pizzeriaOverview.isRestaurantClosed()
        || !(itemsInStorage.size() == 0 && pizzeriaOverview.areAllBakersFinishedWork())) {

      deliveryWorkers.lock.lock();
      try {
        if (pizzeriaOverview.isRestaurantClosed()
            && itemsInStorage.size() == 0
            && pizzeriaOverview.areAllBakersFinishedWork()) {
          break;
        }
        for (int i = 0; i < capacity; i++) {
          Order order = null;
          if (bag.size() != 0) {
            try {
              order = itemsInStorage.poll(WAITING_TIME, TimeUnit.MILLISECONDS);
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
                  && itemsInStorage.size() == 0
                  && pizzeriaOverview.areAllBakersFinishedWork()) {

                System.out.println("We are closed!");

                pizzeriaOverview.closePizzeria();
                break;
              }
              order = itemsInStorage.poll(WAITING_TIME, TimeUnit.MILLISECONDS);
              if (order != null) {
                bag.add(order);
              }
            }
            if (pizzeriaOverview.isRestaurantClosed()) {
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
      if (pizzeriaOverview.isRestaurantClosed()) {
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
