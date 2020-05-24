package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents staff in pizzeria.
 */
class Employees {

  @JsonProperty("pizzaChefs")
  final Baker[] bakers;

  @JsonProperty("deliveryWorkers")
  final DeliveryWorker[] deliveryWorkers;

  Employees(
      @JsonProperty("pizzaChefs") Baker[] bakers,
      @JsonProperty("deliveryWorkers") DeliveryWorker[] deliveryWorkers) {

    this.bakers = bakers;
    this.deliveryWorkers = deliveryWorkers;
  }
}