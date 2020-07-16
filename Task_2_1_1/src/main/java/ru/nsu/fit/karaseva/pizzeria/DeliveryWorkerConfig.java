package ru.nsu.fit.karaseva.pizzeria;

public class DeliveryWorkerConfig {
  private int id;
  private int capacity;
  private int deliveryTime;

  public int getId(){
    return id;
  }
  public void setId(int id){
    this.id = id;
  }
  public int getCapacity(){
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int getDeliveryTime(){
    return deliveryTime;
  }
  public void setDeliveryTime(int deliveryTime){
    this.deliveryTime = deliveryTime;
  }

}
