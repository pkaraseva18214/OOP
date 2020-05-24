package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.stream.IntStream;

/** Class that represents a pizzeria. There are bakers and delivery workers. */
public class Pizzeria {
  private static final int WAITING_TIME_MILLISECONDS = 3000;
  private final Employees employees;
  private final Bakers bakers;
  private final DeliveryWorkers deliveryWorkers;
  private final PizzeriaOverview pizzeriaOverview;
  private final IncomingOrders incomingOrders;
  private final Storage storage;

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
   *
   * @param numberOfOrders total number of orders
   * @return the pizzaRestaurantHeadquarters object
   */
  public PizzeriaOverview start(int numberOfOrders) {
    bakers.run(employees, storage, incomingOrders, pizzeriaOverview);
    deliveryWorkers.run(employees, storage, pizzeriaOverview);
    IntStream.range(0, numberOfOrders).forEach(i -> order());
    closeRestaurant();
    return pizzeriaOverview;
  }

  private void order() {
    System.out.println("Order #" + pizzeriaOverview.getCurrentOrderId() + ".");

    Order order = new Order(pizzeriaOverview.getCurrentOrderId());
    pizzeriaOverview.updateCurrentOrderId();
    incomingOrders.order(order);
  }

  private void closeRestaurant() {
    pizzeriaOverview.closePizzeria();

    while (!incomingOrders.areThereAnyOrders()) {
      try {
        Thread.sleep(WAITING_TIME_MILLISECONDS);
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
        Thread.sleep(WAITING_TIME_MILLISECONDS);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
