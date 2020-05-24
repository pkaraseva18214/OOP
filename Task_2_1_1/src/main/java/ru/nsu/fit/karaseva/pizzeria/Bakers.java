package ru.nsu.fit.karaseva.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Bakers {
  private final List<FutureObjectPair> bakersAndPizzas;
  final Lock lock;

  Bakers() {
    this.bakersAndPizzas = new ArrayList<>();
    lock = new ReentrantLock(true);
  }

  List<FutureObjectPair> getBakersAndPizzas() {
    return bakersAndPizzas;
  }

  void run(
      Employees employees,
      Storage storage,
      IncomingOrders incomingOrders,
      PizzeriaOverview pizzeriaOverview) {

    pizzeriaOverview.setNumberOfBakers(employees.bakers.length);
    ExecutorService executorService = Executors.newFixedThreadPool(employees.bakers.length);

    for (Baker baker : employees.bakers) {
      baker.setBakers(this);
      baker.setPizzeriaOverview(pizzeriaOverview);
      baker.setStorage(storage);
      baker.setIncomingOrders(incomingOrders);

      bakersAndPizzas.add(new FutureObjectPair(baker, executorService.submit(baker)));
    }
  }
}
