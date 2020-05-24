package ru.nsu.fit.karaseva.pizzeria;

/**
 * Class represents important data for pizzeria.
 */
public class PizzeriaOverview {
  private int currentOrderId;
  private int numOfCompletedOrders;
  private int numOfPizzaChefs;
  private int numOfDeliveryWorkers;
  private int numOfPizzaChefsFinishedWork;
  private int numOfDeliveryWorkersFinishedWork;
  private boolean restaurantIsClosed;

  public PizzeriaOverview() {
    currentOrderId = 0;
    numOfCompletedOrders = 0;
    numOfPizzaChefsFinishedWork = 0;
    numOfDeliveryWorkersFinishedWork = 0;
    restaurantIsClosed = false;
  }

  /**
   * If the restaurant is closed.
   * @return true if the restaurant is closed
   */
  public boolean isRestaurantClosed() {
    return restaurantIsClosed;
  }

  /**
   * All bakers finished their work.
   * @return true if all bakers finished their work.
   */
  public boolean areAllBakersFinishedWork() {
    return numOfPizzaChefs == numOfPizzaChefsFinishedWork;
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

  void updateCurrentOrderId() {
    currentOrderId++;
  }

  void completeOrder() {
    numOfCompletedOrders++;
  }

  void setNumberOfBakers(int numOfPizzaChefs) {
    this.numOfPizzaChefs = numOfPizzaChefs;
  }

  void setNumberOfDeliveryWorkers(int numOfDeliveryWorkers) {
    this.numOfDeliveryWorkers = numOfDeliveryWorkers;
  }

  void endShiftForBaker() {
    numOfPizzaChefsFinishedWork++;
  }

  void endShiftForDeliveryWorker() {
    numOfDeliveryWorkersFinishedWork++;
  }

  void closePizzeria() {
    restaurantIsClosed = true;
  }

}
