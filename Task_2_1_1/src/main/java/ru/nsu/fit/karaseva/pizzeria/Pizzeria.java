package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/** Class that represents a pizzeria. There are bakers and delivery workers.
 * It's where the work starts: orders received, employees starts their work and finish it.
 * Then pizzeria will be closed.*/
public class Pizzeria {
  private static int waitingTimeMilliseconds;
  private final Employees employees;
  private final Bakers bakers;
  private final DeliveryWorkers deliveryWorkers;
  private final PizzeriaOverview pizzeriaOverview;
  private final LinkedBlockingQueue<Order> waitingOrders;
 // private final Storage storage;
  private ArrayBlockingQueue<Order> itemsInStorage;

  /**
   * Constructor of Pizzeria Class.
   * @param employeesParameters file that has list of staff.
   * @param itemsInStorage storage of cooked pizzas.
   */
  public Pizzeria(File employeesParameters, ArrayBlockingQueue<Order> itemsInStorage, LinkedBlockingQueue<Order> waitingOrders) {
    JSONReader reader = new JSONReader();
    employees = reader.readParameters(employeesParameters);
    bakers = new Bakers();
    deliveryWorkers = new DeliveryWorkers();
    pizzeriaOverview = new PizzeriaOverview();
    this.itemsInStorage = itemsInStorage;
    this.waitingOrders = waitingOrders;
    setWainingTime(3000);
  }

  /**
   * Get orders, make them, closes the restaurant, and returns the pizzeriaHeadquarters object.
   * @param numberOfOrders total number of orders
   * @return the pizzaRestaurantHeadquarters object
   */
  public PizzeriaOverview start(int numberOfOrders) {
    bakers.run(employees, itemsInStorage, waitingOrders, pizzeriaOverview);
    deliveryWorkers.run(employees, itemsInStorage, pizzeriaOverview);
    IntStream.range(0, numberOfOrders).forEach(i -> {
      try {
        order();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    closePizzeria();
    return pizzeriaOverview;
  }

  private void order() throws InterruptedException {
    System.out.println("Order #" + pizzeriaOverview.getCurrentOrderId() + ".");

    Order order = new Order(pizzeriaOverview.getCurrentOrderId());
    pizzeriaOverview.updateCurrentOrderId();
    waitingOrders.put(order);
  }

  private void closePizzeria() {
    pizzeriaOverview.closePizzeria();

    while (!waitingOrders.isEmpty()) {
      try {
        Thread.sleep(waitingTimeMilliseconds);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    for (FutureObjectPair getBakersAndPizzas : bakers.getBakersAndPizzas()) {
      Baker baker = (Baker) getBakersAndPizzas.object;
      if (baker.isWaitingForOrder()) {
        getBakersAndPizzas.future.cancel(true);
      }
    }

    while (!pizzeriaOverview.areAllBakersFinishedWork()
        || !pizzeriaOverview.areAllDeliveryWorkersFinishedWork()) {

      try {
        Thread.sleep(waitingTimeMilliseconds);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Give you opportunity to set waiting time in pizzeria.
   * @param waitingTime
   */
  public void setWainingTime(int waitingTime){
    waitingTimeMilliseconds = waitingTime;
  }
}
