package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.stream.IntStream;

/** Class that represents a pizzeria. There are bakers and delivery workers. */
public class Pizzeria {
  private static int waitingTimeMilliseconds = 3000;
  private final Employees employees;
  private final Bakers bakers;
  private final DeliveryWorkers deliveryWorkers;
  private final PizzeriaOverview pizzeriaOverview;
  private final IncomingOrders incomingOrders;
  private final Storage storage;

  /**
   * Constructor of Pizzeria Class.
   * @param employeesParameters file that has list of staff.
   * @param storageIn storage of cooked pizzas.
   */
  public Pizzeria(File employeesParameters, int storageIn) {
    JSONReader reader = new JSONReader();
    employees = reader.readParameters(employeesParameters);
    bakers = new Bakers();
    deliveryWorkers = new DeliveryWorkers();
    pizzeriaOverview = new PizzeriaOverview();
    storage = new Storage(storageIn);
    incomingOrders = new IncomingOrders();
  }

  /**
   * Get orders, make them, closes the restaurant, and returns the pizzeriaHeadquarters object.
   * @param numberOfOrders total number of orders
   * @return the pizzaRestaurantHeadquarters object
   */
  public PizzeriaOverview start(int numberOfOrders) {
    bakers.run(employees, storage, incomingOrders, pizzeriaOverview);
    deliveryWorkers.run(employees, storage, pizzeriaOverview);
    IntStream.range(0, numberOfOrders).forEach(i -> order());
    closePizzeria();
    return pizzeriaOverview;
  }

  private void order() {
    System.out.println("Order #" + pizzeriaOverview.getCurrentOrderId() + ".");

    Order order = new Order(pizzeriaOverview.getCurrentOrderId());
    pizzeriaOverview.updateCurrentOrderId();
    incomingOrders.order(order);
  }

  private void closePizzeria() {
    pizzeriaOverview.closePizzeria();

    while (!incomingOrders.areThereAnyOrders()) {
      try {
        Thread.sleep(waitingTimeMilliseconds);
      } catch (InterruptedException e) {  //возникнет, если какой-то другой поток прервет работу данного потока.
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
