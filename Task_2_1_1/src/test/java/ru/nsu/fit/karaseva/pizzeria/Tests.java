package ru.nsu.fit.karaseva.pizzeria;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class Tests {
  @Test
  public void test1() {
    JSONReader jsonReader = new JSONReader();
    File employeesParameters = new File("src/main/resources/input");
    Employees employees = jsonReader.readParameters(employeesParameters);
    Assert.assertEquals(3, employees.bakers.length);
    Assert.assertEquals(3, employees.deliveryWorkers.length);

    DeliveryWorkers deliveryWorkers = new DeliveryWorkers();
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    Storage storage = new Storage(9);

    DeliveryWorker deliveryWorker1 = new DeliveryWorker(1, 5000, 3);
    DeliveryWorker deliveryWorker2 = new DeliveryWorker(2, 6000, 3);
    DeliveryWorker deliveryWorker3 = new DeliveryWorker(3, 5000, 3);
    deliveryWorker1.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker1.setPizzaRestaurantHeadquarters(pizzeriaOverview);
    deliveryWorker1.setStorage(storage);
    deliveryWorker2.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker2.setPizzaRestaurantHeadquarters(pizzeriaOverview);
    deliveryWorker2.setStorage(storage);
    deliveryWorker3.setDeliveryWorkers(deliveryWorkers);
    deliveryWorker3.setPizzaRestaurantHeadquarters(pizzeriaOverview);
    deliveryWorker3.setStorage(storage);
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
  public void test2(){
    JSONReader jsonReader = new JSONReader();
    File employeesParameters = new File("src/main/resources/input");
    Employees employees = jsonReader.readParameters(employeesParameters);

    Assert.assertEquals(3, employees.bakers.length);
    Assert.assertEquals(1, employees.bakers[0].getId());
    Assert.assertEquals(2, employees.bakers[1].getId());
    Assert.assertEquals(10000, employees.bakers[0].getCookingTime());
    Assert.assertEquals(10000, employees.bakers[2].getCookingTime());

    Assert.assertEquals(2, employees.deliveryWorkers[1].getId());
    Assert.assertEquals(3, employees.deliveryWorkers[2].getId());
    Assert.assertEquals(6000, employees.deliveryWorkers[1].getDeliveryTime());
    Assert.assertEquals(5000, employees.deliveryWorkers[2].getDeliveryTime());
    Assert.assertEquals(3, employees.deliveryWorkers[0].getNumOfPizzasCanCarry());
    Assert.assertEquals(3, employees.deliveryWorkers[2].getNumOfPizzasCanCarry());
  }

  @Test
  public void test3(){
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

  @Test
  public void test4(){
    File employeesParameters = new File("src/main/resources/input");
    Pizzeria pizzeria = new Pizzeria(employeesParameters, 9);
    PizzeriaOverview pizzeriaOverview = pizzeria.start(5);

    Assert.assertTrue(pizzeriaOverview.areAllBakersFinishedWork());
    Assert.assertTrue(pizzeriaOverview.areAllDeliveryWorkersFinishedWork());
    Assert.assertTrue(pizzeriaOverview.isRestaurantClosed());
  }

  @Test
  public void test5(){
    IncomingOrders incomingOrders = new IncomingOrders();
    Bakers bakers = new Bakers();
    PizzeriaOverview pizzeriaOverview = new PizzeriaOverview();
    Storage storage = new Storage(9);

    Baker baker1 = new Baker(1, 10000);
    Baker baker2 = new Baker(2, 10000);
    Baker baker3 = new Baker(3, 10000);
    baker1.setBakers(bakers);
    baker1.setPizzeriaOverview(pizzeriaOverview);
    baker1.setStorage(storage);
    baker1.setIncomingOrders(incomingOrders);
    baker2.setBakers(bakers);
    baker2.setPizzeriaOverview(pizzeriaOverview);
    baker2.setStorage(storage);
    baker2.setIncomingOrders(incomingOrders);
    baker3.setBakers(bakers);
    baker3.setPizzeriaOverview(pizzeriaOverview);
    baker3.setStorage(storage);
    baker3.setIncomingOrders(incomingOrders);
    pizzeriaOverview.setNumberOfBakers(3);

    Thread thread1 = new Thread(baker1);
    Thread thread2 = new Thread(baker2);
    Thread thread3 = new Thread(baker3);
    thread1.start();
    thread2.start();
    thread3.start();
    incomingOrders.order(new Order(1));
    incomingOrders.order(new Order(2));
    incomingOrders.order(new Order(3));
    incomingOrders.order(new Order(4));

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
