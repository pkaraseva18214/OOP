package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;

public class WorkTest {
  @Test
  public void allDeliveryWorkersFinishedTheirWorkSuccessfully() {
    JSONReader jsonReader = new JSONReader();
    File employeesParameters = new File("src/main/resources/input");
    Employees employees = jsonReader.readParameters(employeesParameters);
    Assert.assertEquals(3, employees.getNumberOfBakers());
    Assert.assertEquals(3, employees.getNumberOfDeliveryWorkers());

    DeliveryWorkers deliveryWorkers = new DeliveryWorkers();
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<Order>(9);

    DeliveryWorker deliveryWorker1 = new DeliveryWorker(1, 5000, 3);
    DeliveryWorker deliveryWorker2 = new DeliveryWorker(2, 6000, 3);
    DeliveryWorker deliveryWorker3 = new DeliveryWorker(3, 5000, 3);
    deliveryWorker1.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker1.setPizzeriaOverview(pizzeriaOverview);
    deliveryWorker1.setStorage(itemsInStorage);
    deliveryWorker2.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker2.setPizzeriaOverview(pizzeriaOverview);
    deliveryWorker2.setStorage(itemsInStorage);
    deliveryWorker3.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker3.setPizzeriaOverview(pizzeriaOverview);
    deliveryWorker3.setStorage(itemsInStorage);
    pizzeriaOverview.setNumberOfDeliveryWorkers(3);

    Thread thread1 = new Thread(deliveryWorker1);
    Thread thread2 = new Thread(deliveryWorker2);
    Thread thread3 = new Thread(deliveryWorker3);
    thread1.start();
    thread2.start();
    thread3.start();
    pizzeriaOverview.closePizzeria();

    try {
      thread1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    try {
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    try {
      thread3.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Assert.assertTrue(pizzeriaOverview.areAllDeliveryWorkersFinishedWork());
  }

  @Test
  public void pizzeriaAndAllStaffFinishedTheirWorkSuccessfully() {
    File employeesParameters = new File("src/main/resources/input");
    LinkedBlockingQueue<Order> waitingOrders = new LinkedBlockingQueue<Order>();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<Order>(9);
    Pizzeria pizzeria = new Pizzeria(employeesParameters, itemsInStorage, waitingOrders);
    PizzeriaOverview pizzeriaOverview = pizzeria.start(5);

    Assert.assertTrue(pizzeriaOverview.areAllBakersFinishedWork());
    Assert.assertTrue(pizzeriaOverview.areAllDeliveryWorkersFinishedWork());
    Assert.assertTrue(pizzeriaOverview.isRestaurantClosed());
  }

  @Test
  public void allBakersFinishedTheirWorkSuccessfully() throws InterruptedException {
    LinkedBlockingQueue<Order> waitingOrders = new LinkedBlockingQueue<Order>();
    Bakers bakers = new Bakers();
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<Order>(9);

    Baker baker1 = new Baker(1, 10000);
    Baker baker2 = new Baker(2, 10000);
    Baker baker3 = new Baker(3, 10000);
    baker1.setBakers(bakers);
    baker1.setPizzeriaOverview(pizzeriaOverview);
    baker1.setStorage(itemsInStorage);
    baker1.setIncomingOrders(waitingOrders);
    baker2.setBakers(bakers);
    baker2.setPizzeriaOverview(pizzeriaOverview);
    baker2.setStorage(itemsInStorage);
    baker2.setIncomingOrders(waitingOrders);
    baker3.setBakers(bakers);
    baker3.setPizzeriaOverview(pizzeriaOverview);
    baker3.setStorage(itemsInStorage);
    baker3.setIncomingOrders(waitingOrders);
    pizzeriaOverview.setNumberOfBakers(3);

    Thread thread1 = new Thread(baker1);
    Thread thread2 = new Thread(baker2);
    Thread thread3 = new Thread(baker3);
    thread1.start();
    thread2.start();
    thread3.start();
    waitingOrders.put(new Order(1));
    waitingOrders.put(new Order(2));
    waitingOrders.put(new Order(3));
    waitingOrders.put(new Order(4));

    pizzeriaOverview.closePizzeria();

    try {
      thread1.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    try {
      thread2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    try {
      thread3.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Assert.assertTrue(pizzeriaOverview.areAllBakersFinishedWork());
  }
}
