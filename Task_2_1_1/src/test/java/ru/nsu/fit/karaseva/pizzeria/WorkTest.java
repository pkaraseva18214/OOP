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
    File deliveryFile = new File("src/main/resources/input");
    File bakerFile = new File("src/main/resources/baker");
    Employees employees = new Employees(jsonReader.readBakers(bakerFile), jsonReader.readDeliveryWorkers(deliveryFile));

    Assert.assertEquals(3, employees.getNumberOfBakers());
    Assert.assertEquals(3, employees.getNumberOfDeliveryWorkers());

    DeliveryWorkers deliveryWorkers = new DeliveryWorkers();
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<>(9);

    DeliveryWorkerConfig dwc1 = new DeliveryWorkerConfig();
    dwc1.setId(1);
    dwc1.setDeliveryTime(5000);
    dwc1.setCapacity(3);
    DeliveryWorkerConfig dwc2 = new DeliveryWorkerConfig();
    dwc2.setId(2);
    dwc2.setDeliveryTime(6000);
    dwc1.setCapacity(3);
    DeliveryWorkerConfig dwc3 = new DeliveryWorkerConfig();
    dwc3.setId(3);
    dwc3.setDeliveryTime(5000);
    dwc3.setCapacity(3);
    DeliveryWorker deliveryWorker1 = new DeliveryWorker(dwc1);
    DeliveryWorker deliveryWorker2 = new DeliveryWorker(dwc2);
    DeliveryWorker deliveryWorker3 = new DeliveryWorker(dwc3);
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
    File deliveryFile = new File("src/main/resources/input");
    File bakerFile = new File("src/main/resources/baker");
    LinkedBlockingQueue<Order> waitingOrders = new LinkedBlockingQueue<>();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<>(9);
    Pizzeria pizzeria = new Pizzeria(bakerFile, deliveryFile, itemsInStorage, waitingOrders);
    PizzeriaOverview pizzeriaOverview = pizzeria.start(5);

    Assert.assertTrue(pizzeriaOverview.areAllBakersFinishedWork());
    Assert.assertTrue(pizzeriaOverview.areAllDeliveryWorkersFinishedWork());
    Assert.assertTrue(pizzeriaOverview.isRestaurantClosed());
  }

  @Test
  public void allBakersFinishedTheirWorkSuccessfully() throws InterruptedException {
    LinkedBlockingQueue<Order> waitingOrders = new LinkedBlockingQueue<>();
    Bakers bakers = new Bakers();
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<>(9);

    BakerConfig bakerConfig1 = new BakerConfig();
    bakerConfig1.setId(1);
    bakerConfig1.setCookingTime(10000);
    BakerConfig bakerConfig2 = new BakerConfig();
    bakerConfig2.setId(2);
    bakerConfig2.setCookingTime(10000);
    BakerConfig bakerConfig3 = new BakerConfig();
    bakerConfig3.setId(3);
    bakerConfig3.setCookingTime(10000);
    Baker baker1 = new Baker(bakerConfig1);
    Baker baker2 = new Baker(bakerConfig2);
    Baker baker3 = new Baker(bakerConfig3);
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
