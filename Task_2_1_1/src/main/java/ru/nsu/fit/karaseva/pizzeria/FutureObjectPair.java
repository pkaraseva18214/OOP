package ru.nsu.fit.karaseva.pizzeria;

import java.util.concurrent.Future;

class FutureObjectPair {
  final Object object;
  final Future<?> future; //данная конструкция называеся wildcard, можно использовать, если аргумент типа не определен.

  FutureObjectPair(Object object, Future<?> future) {
    this.object = object;
    this.future = future;
  }
}