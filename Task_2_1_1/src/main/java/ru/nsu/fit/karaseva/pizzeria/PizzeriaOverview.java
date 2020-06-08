package ru.nsu.fit.karaseva.pizzeria;

/**
 * Class represents important data for pizzeria.
 */
public class PizzeriaOverview {
  private int currentOrderId;
  private int numOfBakers;
  private int numOfDeliveryWorkers;
  private int numOfBakersFinishedWork;
  private int numOfDeliveryWorkersFinishedWork;
  private boolean pizzeriaIsClosed;

  /**
   * Constructor of PizzeriaOverview Class.
   */
  public PizzeriaOverview() {
    currentOrderId = 0;
    numOfBakersFinishedWork = 0;
    numOfDeliveryWorkersFinishedWork = 0;
    pizzeriaIsClosed = false;
  }

  /**
   * If the restaurant is closed.
   * @return true if the restaurant is closed
   */
  public boolean isRestaurantClosed() {
    return pizzeriaIsClosed;
  }

  /**
   * All bakers finished their work.
   * @return true if all bakers finished their work.
   */
  public boolean areAllBakersFinishedWork() {
    return  numOfBakers == numOfBakersFinishedWork;
  }

  /**
   * All delivery workers finished their work.
   * @return true if all delivery workers finished their work.
   */
  public boolean areAllDeliveryWorkersFinishedWork() {
    return numOfDeliveryWorkers == numOfDeliveryWorkersFinishedWork;
  }

  /**
   * Returns current order ID.
   * @return current order ID
   */
  public int getCurrentOrderId() {
    return currentOrderId;
  }

  /**
   * Updates order's id.
   */
  void updateCurrentOrderId() {
    currentOrderId++;
  }

  /**
   * Sets number of bakers.
   * @param numOfBakers
   */
  void setNumberOfBakers(int numOfBakers) {
    this.numOfBakers = numOfBakers;
  }

  /**
   * Sets number of delivery workers.
   * @param numOfDeliveryWorkers
   */
  void setNumberOfDeliveryWorkers(int numOfDeliveryWorkers) {
    this.numOfDeliveryWorkers = numOfDeliveryWorkers;
  }

  /**
   * Ends shift for baker.
   */
  void endShiftForBaker() {
    numOfBakersFinishedWork++;
  }

  /**
   * Ends shift for delivery worker.
   */
  void endShiftForDeliveryWorker() {
    numOfDeliveryWorkersFinishedWork++;
  }

  /**
   * Set status of pizzeria as closed.
   */
  void closePizzeria() {
    pizzeriaIsClosed = true;
  }

}
