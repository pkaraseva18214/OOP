package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

/** Class that represents staff in pizzeria. */
public class Employees {
  public final List<Baker> bakers;
  private final List<DeliveryWorker> deliveryWorkers;

  public Employees(
      @JsonProperty("bakers") List<Baker> bakers,
      @JsonProperty("deliveryWorkers") List<DeliveryWorker> deliveryWorkers) {

    this.bakers = bakers;
    this.deliveryWorkers = deliveryWorkers;
  }

  /**
   * Returns number of bakers.
   *
   * @return
   */
  public int getNumberOfBakers() {
    return bakers.size();
  }

  /**
   * Returns number of delivery workers.
   *
   * @return
   */
  public int getNumberOfDeliveryWorkers() {
    return deliveryWorkers.size();
  }

  /**
   * Returns list of bakers.
   * @return
   */
  public List<Baker> getBakers() {
    return Collections.unmodifiableList(bakers);
  }

  /**
   * Returns list of delivery workers.
   * @return
   */
  public List<DeliveryWorker> getDeliveryWorkers() {
    return Collections.unmodifiableList(deliveryWorkers);
  }

}
