package ru.nsu.fit.karaseva.snake;

import static ru.nsu.fit.karaseva.snake.Direction.NORTH;

import java.util.concurrent.ThreadLocalRandom;

/** The Fruit represents a small object that can be eaten by a Snake */
public class Fruit {

  private Position pos;
  private final Snake snake;

  public Fruit(Snake snake) {
    this.snake = snake;
  }

  /** Generates a random Position for the fruit. */
  public void generateRandomPosition() {
    int min = 0;
    int max = snake.getFieldsize() - 1;
    int x = ThreadLocalRandom.current().nextInt(min, max + 1);
    int y = ThreadLocalRandom.current().nextInt(min, max + 1);

    if (snake.isSnakePosition(x, y)) {
      generateRandomPosition();
    } else {
      try {
        pos = new Position(x, y, NORTH);
      } catch (InvalidSnakePositionException e) {
        e.printStackTrace();
      }
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
}
