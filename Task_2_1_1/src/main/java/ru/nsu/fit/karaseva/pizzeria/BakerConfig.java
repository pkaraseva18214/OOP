package ru.nsu.fit.karaseva.pizzeria;

/**
 * Class that represents read data from file for baker.
 */
public class BakerConfig {
  private int id;
  private int cookingTime;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCookingTime() {
    return cookingTime;
  }

  public void setCookingTime(int cookingTime) {
    this.cookingTime = cookingTime;
  }
}
