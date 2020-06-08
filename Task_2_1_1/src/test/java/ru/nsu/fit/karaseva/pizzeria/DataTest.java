package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;


public class DataTest {
  @Test
  public void correctlyReadData(){
    JSONReader jsonReader = new JSONReader();
    File employeesParameters = new File("src/main/resources/input");
    Employees employees = jsonReader.readParameters(employeesParameters);

    Assert.assertEquals(3, employees.getNumberOfBakers());
    Assert.assertEquals(1, employees.getBaker(0).getId());
    Assert.assertEquals(2, employees.getBaker(1).getId());
    Assert.assertEquals(10000, employees.getBaker(0).getCookingTime());
    Assert.assertEquals(10000, employees.getBaker(2).getCookingTime());

    Assert.assertEquals(2, employees.getDeliveryWorker(1).getId());
    Assert.assertEquals(3, employees.getDeliveryWorker(2).getId());
    Assert.assertEquals(6000, employees.getDeliveryWorker(1).getDeliveryTime());
    Assert.assertEquals(5000, employees.getDeliveryWorker(2).getDeliveryTime());
    Assert.assertEquals(3, employees.getDeliveryWorker(0).getCapacity());
    Assert.assertEquals(3, employees.getDeliveryWorker(2).getCapacity());
  }

  @Test
  public void correctlyReadAndInterpretedData(){
    Storage storage = new Storage(9);
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    IncomingOrders incomingOrders = new IncomingOrders();
    DeliveryWorkers deliveryWorkers = new DeliveryWorkers();
    Bakers bakers = new Bakers();
    int numberOfBakers = 0;

    JSONReader jsonReader = new JSONReader();
    File employeesParameters = new File("src/main/resources/input");
    Employees employees = jsonReader.readParameters(employeesParameters);

    deliveryWorkers.run(employees, storage, pizzeriaOverview);
    bakers.run(employees, storage, incomingOrders, pizzeriaOverview);
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
