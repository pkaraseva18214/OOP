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

  void run(Employees employees, PizzeriaOverview pizzeriaOverview) {
    pizzeriaOverview.setNumberOfBakers(employees.getNumberOfBakers());
    ExecutorService executorService = Executors.newFixedThreadPool(employees.getNumberOfBakers());

    for (Baker baker : employees.bakers) {
      baker.setBakers(this);
      bakersAndPizzas.add(new FutureObjectPair(baker, executorService.submit(baker)));
    }
  }
}
