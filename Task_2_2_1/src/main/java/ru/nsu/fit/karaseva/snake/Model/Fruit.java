package ru.nsu.fit.karaseva.snake.model;

import java.util.concurrent.ThreadLocalRandom;

/** The Fruit represents a small object that can be eaten by a Snake */
public class Fruit {
  private Position pos;
  private Field field;

  public Fruit(Field field) {
    this.field = field;
  }

  /** Generates a random Position for the fruit. */
  public void generateRandomPosition() throws InvalidPositionException {
    int min = 0;
    int max = field.getFieldSize() - 1;
    int x = ThreadLocalRandom.current().nextInt(min, max + 1);
    int y = ThreadLocalRandom.current().nextInt(min, max + 1);
    Position p = new Position(x, y);
    if (!field.isCellEmpty(p)) {
      generateRandomPosition();
    } else {
      pos = p;
      field.occupyCell(pos, 2);
    }
  }
  /**
   * Determines whether a fruit is set on the given position.
   *
   * @param x The x value of the position
   * @param y The y value of the position
   * @return true if the fruit has this position, false otherwise
   */
  public boolean isFruitPosition(int x, int y) {
    if (pos != null) {
      return pos.getX() == x && pos.getY() == y;
    }
    return false;
  }

  public Position getFruitPosition(){
    return pos;
  }
}