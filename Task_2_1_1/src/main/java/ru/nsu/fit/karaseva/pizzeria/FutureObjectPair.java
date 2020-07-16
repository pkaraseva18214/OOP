package ru.nsu.fit.karaseva.pizzeria;

import java.util.concurrent.Future;

class FutureObjectPair {
  final Object object;
  final Future<?> future;

  FutureObjectPair(Object object, Future<?> future) {
    this.object = object;
    this.future = future;
  }
}
