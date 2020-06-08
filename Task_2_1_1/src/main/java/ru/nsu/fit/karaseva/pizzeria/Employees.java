package ru.nsu.fit.karaseva.pizzeria;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Class that represents staff in pizzeria.
 */
public class Employees {
  private final List<Baker> bakers;
  private final List<DeliveryWorker> deliveryWorkers;

  Employees(
      @JsonProperty("bakers") List<Baker> bakers,
      @JsonProperty("deliveryWorkers") List<DeliveryWorker> deliveryWorkers) {

    this.bakers = bakers;
    this.deliveryWorkers = deliveryWorkers;
  }

  /**
   * Returns number of bakers.
   * @return
   */
  int getNumberOfBakers(){
    return bakers.size();
  }

  /**
   * Returns number of delivery workers.
   * @return
   */
  int getNumberOfDeliveryWorkers(){
    return deliveryWorkers.size();
  }

  /**
   * Returns baker.
   * @param i - number of barker.
   * @return
   */
  Baker getBaker(int i){
    return bakers.get(i);
  }

  /**
   * Returns delivery worker.
   * @param i - number of delivery worker.
   * @return
   */
  DeliveryWorker getDeliveryWorker(int i){
    return deliveryWorkers.get(i);
  }
}