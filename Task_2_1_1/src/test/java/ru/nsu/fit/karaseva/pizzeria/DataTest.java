package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;

public class DataTest {
  @Test
  public void correctlyReadData() {
    JSONReader jsonReader = new JSONReader();
    File deliveryFile = new File("src/main/resources/input");
    File bakerFile = new File("src/main/resources/baker");
    Employees employees =
        new Employees(
            jsonReader.readBakers(bakerFile), jsonReader.readDeliveryWorkers(deliveryFile));

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

    JSONReader jsonReader = new JSONReader();
    File deliveryFile = new File("src/main/resources/input");
    File bakerFile = new File("src/main/resources/baker");
    Employees employees =
        new Employees(
            jsonReader.readBakers(bakerFile), jsonReader.readDeliveryWorkers(deliveryFile));

    deliveryWorkers.run(employees, itemsInStorage, pizzeriaOverview);
    bakers.run(employees, itemsInStorage, waitingOrders, pizzeriaOverview);
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
