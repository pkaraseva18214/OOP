package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;

public class WorkTest {
  @Test
  public void allDeliveryWorkersFinishedTheirWorkSuccessfully() {
    BakerConfig bc1 = new BakerConfig();
    bc1.setCookingTime(10000);
    bc1.setId(1);
    BakerConfig bc2 = new BakerConfig();
    bc2.setCookingTime(10000);
    bc2.setId(2);
    BakerConfig bc3 = new BakerConfig();
    bc3.setCookingTime(10000);
    bc3.setId(3);
    List<BakerConfig> bakerConfigs = new LinkedList<BakerConfig>();
    bakerConfigs.add(bc1);
    bakerConfigs.add(bc2);
    bakerConfigs.add(bc3);

    DeliveryWorkerConfig dw1 = new DeliveryWorkerConfig();
    dw1.setCapacity(3);
    dw1.setDeliveryTime(5000);
    dw1.setId(1);
    DeliveryWorkerConfig dw2 = new DeliveryWorkerConfig();
    dw2.setCapacity(3);
    dw2.setDeliveryTime(6000);
    dw2.setId(2);
    DeliveryWorkerConfig dw3 = new DeliveryWorkerConfig();
    dw3.setCapacity(3);
    dw3.setDeliveryTime(5000);
    dw3.setId(3);
    List<DeliveryWorkerConfig> deliveryWorkerConfigs = new LinkedList<DeliveryWorkerConfig>();
    deliveryWorkerConfigs.add(dw1);
    deliveryWorkerConfigs.add(dw2);
    deliveryWorkerConfigs.add(dw3);

    DeliveryWorkers deliveryWorkers = new DeliveryWorkers();
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<>(9);
    Employees employees =
        new Employees(
            bakerConfigs,
            deliveryWorkerConfigs,
            itemsInStorage,
            new LinkedBlockingQueue<Order>(),
            pizzeriaOverview);

    Assert.assertEquals(3, employees.getNumberOfBakers());
    Assert.assertEquals(3, employees.getNumberOfDeliveryWorkers());

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
    DeliveryWorker deliveryWorker1 = new DeliveryWorker(dwc1, pizzeriaOverview, itemsInStorage);
    DeliveryWorker deliveryWorker2 = new DeliveryWorker(dwc2, pizzeriaOverview, itemsInStorage);
    DeliveryWorker deliveryWorker3 = new DeliveryWorker(dwc3, pizzeriaOverview, itemsInStorage);
    deliveryWorker1.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker2.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker3.setDeliveryWorkers(deliveryWorkers);
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
  public void pizzeriaAndAllStaffFinishedTheirWorkSuccessfully() throws InterruptedException {
    File deliveryFile = new File("src/test/java/ru/nsu/fit/karaseva/pizzeria/deliveryWorkers");
    File bakerFile = new File("src/test/java/ru/nsu/fit/karaseva/pizzeria/bakers");
    LinkedBlockingQueue<Order> waitingOrders = new LinkedBlockingQueue<>();
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<>(9);
    Pizzeria pizzeria = new Pizzeria(bakerFile, deliveryFile, itemsInStorage, waitingOrders);
    PizzeriaOverview pizzeriaOverview = pizzeria.start();

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
    Baker baker1 = new Baker(bakerConfig1, waitingOrders, pizzeriaOverview, itemsInStorage);
    Baker baker2 = new Baker(bakerConfig2, waitingOrders, pizzeriaOverview, itemsInStorage);
    Baker baker3 = new Baker(bakerConfig3, waitingOrders, pizzeriaOverview, itemsInStorage);
    baker1.setBakers(bakers);
    baker2.setBakers(bakers);
    baker3.setBakers(bakers);
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

    Thread.currentThread().sleep(3000);
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
