package ru.nsu.fit.karaseva.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DeliveryWorkers {
  final Lock lock;
  private final List<Future<?>> orders;

  DeliveryWorkers() {
    orders = new ArrayList<>();
    lock = new ReentrantLock(true);
  }

  List<Future<?>> getOrders() {
    return orders;
  }

  void run(
      Employees employees,
      Storage storage,
      PizzeriaOverview pizzeriaOverview) {

    pizzeriaOverview.setNumberOfDeliveryWorkers(employees.deliveryWorkers.length);
    ExecutorService executor = Executors.newFixedThreadPool(employees.deliveryWorkers.length);

    for (DeliveryWorker deliveryWorker : employees.deliveryWorkers) {
      deliveryWorker.setDeliveryWorkers(this);
      deliveryWorker.setPizzaRestaurantHeadquarters(pizzeriaOverview);
      deliveryWorker.setStorage(storage);

      Future<?> future = executor.submit(deliveryWorker);
      orders.add(future);
    }
  }
}