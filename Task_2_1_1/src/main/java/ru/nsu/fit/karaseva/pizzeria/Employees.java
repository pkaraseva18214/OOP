package ru.nsu.fit.karaseva.pizzeria;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** Class that represents staff in pizzeria. */
public class Employees {
  public List<Baker> bakers;
  private List<DeliveryWorker> deliveryWorkers;

  public Employees(List<BakerConfig> bakerConfigs, List<DeliveryWorkerConfig> deliveryWorkersConfig) {
    bakers = new LinkedList<>();
    for(BakerConfig bakerConfig : bakerConfigs){
      bakers.add(new Baker(bakerConfig));
    }
    deliveryWorkers = new LinkedList<>();
    for(DeliveryWorkerConfig deliveryWorkerConfig : deliveryWorkersConfig){
      deliveryWorkers.add(new DeliveryWorker(deliveryWorkerConfig));
    }
  }

  /**
   * @return number of bakers.
   */
  public int getNumberOfBakers() {
    return bakers.size();
  }

  /**
   * @return number of delivery workers.
   */
  public int getNumberOfDeliveryWorkers() {
    return deliveryWorkers.size();
  }

  /**
   * @return list of bakers.
   */
  public List<Baker> getBakers() {
    return Collections.unmodifiableList(bakers);
  }

  /**
   * @return list of delivery workers.
   */
  public List<DeliveryWorker> getDeliveryWorkers() {
    return Collections.unmodifiableList(deliveryWorkers);
  }
}
