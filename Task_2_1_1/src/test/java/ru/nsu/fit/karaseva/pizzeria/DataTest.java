package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;

public class DataTest {

  @Test
  public void correctlyReadData() {
    JSONReader jsonReader = new JSONReader();
    File deliveryFile = new File("src/test/java/ru/nsu/fit/karaseva/pizzeria/deliveryWorkers");
    File bakerFile = new File("src/test/java/ru/nsu/fit/karaseva/pizzeria/bakers");
    List<BakerConfig> bakersConfig = jsonReader.readBakers(bakerFile);
    for (int i = 0; i < 3; i++) {
      Assert.assertEquals(i + 1, bakersConfig.get(i).getId());
      Assert.assertEquals(10000, bakersConfig.get(i).getCookingTime());
    }
    List<DeliveryWorkerConfig> deliveryWorkerConfigs = jsonReader.readDeliveryWorkers(deliveryFile);
    Assert.assertEquals(1, deliveryWorkerConfigs.get(0).getId());
    Assert.assertEquals(2, deliveryWorkerConfigs.get(1).getId());
    Assert.assertEquals(3, deliveryWorkerConfigs.get(2).getId());
    Assert.assertEquals(5000, deliveryWorkerConfigs.get(0).getDeliveryTime());
    Assert.assertEquals(6000, deliveryWorkerConfigs.get(1).getDeliveryTime());
    Assert.assertEquals(5000, deliveryWorkerConfigs.get(2).getDeliveryTime());
    Assert.assertEquals(3, deliveryWorkerConfigs.get(0).getCapacity());
    Assert.assertEquals(3, deliveryWorkerConfigs.get(1).getCapacity());
    Assert.assertEquals(3, deliveryWorkerConfigs.get(2).getCapacity());
  }

  @Test
  public void correctlyConstructedClasses() {
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
    Employees employees =
        new Employees(
            bakerConfigs,
            deliveryWorkerConfigs,
            new ArrayBlockingQueue<Order>(9),
            new LinkedBlockingQueue<Order>(),
            new PizzeriaOverview());

    Assert.assertEquals(3, employees.getNumberOfBakers());
    Assert.assertEquals(1, employees.getBakers().get(0).getId());
    Assert.assertEquals(2, employees.getBakers().get(1).getId());
    Assert.assertEquals(10000, employees.getBakers().get(0).getCookingTime());
    Assert.assertEquals(10000, employees.getBakers().get(2).getCookingTime());

    Assert.assertEquals(2, employees.getDeliveryWorkers().get(1).getId());
    Assert.assertEquals(3, employees.getDeliveryWorkers().get(2).getId());
    Assert.assertEquals(6000, employees.getDeliveryWorkers().get(1).getDeliveryTime());
    Assert.assertEquals(5000, employees.getDeliveryWorkers().get(2).getDeliveryTime());
    Assert.assertEquals(3, employees.getDeliveryWorkers().get(0).getCapacity());
    Assert.assertEquals(3, employees.getDeliveryWorkers().get(2).getCapacity());
  }

  @Test
  public void correctlyReadAndInterpretedData() {
    ArrayBlockingQueue<Order> itemsInStorage = new ArrayBlockingQueue<>(9);
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    LinkedBlockingQueue<Order> waitingOrders = new LinkedBlockingQueue<>();
    DeliveryWorkers deliveryWorkers = new DeliveryWorkers();
    Bakers bakers = new Bakers();
    int numberOfBakers = 0;

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

    Employees employees =
        new Employees(
            bakerConfigs, deliveryWorkerConfigs, itemsInStorage, waitingOrders, pizzeriaOverview);

    deliveryWorkers.run(employees, pizzeriaOverview);
    bakers.run(employees, pizzeriaOverview);
    Baker baker;
    for (FutureObjectPair a : bakers.getBakersAndPizzas()) {
      numberOfBakers++;
      baker = (Baker) a.object;
      Assert.assertTrue(baker.getId() >= 1);
    }
    Assert.assertEquals(3, numberOfBakers);
    Assert.assertEquals(3, deliveryWorkers.getOrders().size());
  }
}
