package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;

/**
 * Class that represents a pizzeria. There are bakers and delivery workers. It's where the work
 * starts: orders received, employees starts their work and finish it. Then pizzeria will be closed.
 */
public class Pizzeria {
  private static int waitingTimeMilliseconds;
  private final Employees employees;
  private final Bakers bakers;
  private final DeliveryWorkers deliveryWorkers;
  private final PizzeriaOverview pizzeriaOverview;
  private final LinkedBlockingQueue<Order> waitingOrders;
  private ArrayBlockingQueue<Order> itemsInStorage;
  private static long workingTime = 5000;

  /**
   * @param bakerFile file from which bakers data is read
   * @param deliveryFile file from which delivery workers data is read
   * @param itemsInStorage storage
   * @param waitingOrders waiting orders
   */
  public Pizzeria(
      File bakerFile,
      File deliveryFile,
      ArrayBlockingQueue<Order> itemsInStorage,
      LinkedBlockingQueue<Order> waitingOrders) {
    JSONReader jsonReader = new JSONReader();
    employees =
        new Employees(
            jsonReader.readBakers(bakerFile), jsonReader.readDeliveryWorkers(deliveryFile));
    bakers = new Bakers();
    deliveryWorkers = new DeliveryWorkers();
    pizzeriaOverview = new PizzeriaOverview();
    this.itemsInStorage = itemsInStorage;
    this.waitingOrders = waitingOrders;
    setWainingTime(3000);
  }

  /**
   * Get orders, make them, closes the restaurant, and returns the pizzeriaHeadquarters object.
   * @return the pizzeriaOverview object
   */
  public PizzeriaOverview start(int numberOfOrders) throws InterruptedException {
    bakers.run(employees, itemsInStorage, waitingOrders, pizzeriaOverview);
    deliveryWorkers.run(employees, itemsInStorage, pizzeriaOverview);
    IntStream.range(0, numberOfOrders)
        .forEach(
            i -> {
              try {
                order();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
    Thread.sleep(workingTime);
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
        System.out.println(e + "caused by " + e.getCause());
        System.exit(1);
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
        System.out.println(e + "caused by " + e.getCause());
        System.exit(1);
      }
    }
  }

  /**
   * Give you opportunity to set waiting time in pizzeria.
   *
   * @param waitingTime waiting time in pizzeria.
   */
  public void setWainingTime(int waitingTime) {
    waitingTimeMilliseconds = waitingTime;
  }
}
