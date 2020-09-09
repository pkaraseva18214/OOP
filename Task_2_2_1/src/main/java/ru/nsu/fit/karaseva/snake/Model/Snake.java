package ru.nsu.fit.karaseva.snake.model;

import static ru.nsu.fit.karaseva.snake.model.Direction.NORTH;
import java.util.LinkedList;

/**
 * The Snake class allows to create a snake object that can move within a certain field size and can
 * do any actions related to the game Snake.
 */
public class Snake {
  private LinkedList<Position> snakeBody;
  private final int fieldSize;
  private boolean snakeAlive = true;
  private int score;
  private int death = 20;
  private Direction direction;
  private Field field;
  /**
   * Creates a snake that can crawl in a fixed sized field of size max.
   *
   * @param max The maximum field size in which the snake can crawl
   */
  public Snake(int max, Field field) throws InvalidPositionException {
    this.field = field;
    snakeBody = new LinkedList<>();
    fieldSize = max;
    score = 0;
    Position p1 = new Position(max / 2, max / 2);
    Position p2 = new Position(max / 2, max / 2 + 1);
    snakeBody.add(p2);
    snakeBody.add(p1);
    field.occupyCell(p1, 1);
    field.occupyCell(p2, 1);
    direction = NORTH;
  }

  public boolean isSnakeAlive() {
    return snakeAlive;
  }
  /**
   * Checks whether the snake has a body element at this position.
   *
   * @param x The row value
   * @param y The column value
   * @return true, if position is set with a body element
   */
  public boolean isSnakePosition(int x, int y) {
    boolean hasThisPosition = false;
    for (Position pos : snakeBody) {
      if (pos.getX() == x && pos.getY() == y) {
        hasThisPosition = true;
        break;
      }
    }
    return hasThisPosition;
  }
  /**
   * Checks whether the snake head has the same position as a fruit.
   *
   * @param fruit The fruit that is available
   * @return true, if the snake head touched the fruit
   */
  public boolean snakeReachedFruit(Fruit fruit) {
    return fruit.isFruitPosition(snakeBody.getFirst().getX(), snakeBody.getFirst().getY());
  }
  /**
   * Performs action that is taken, when a fruit is eaten: Adding body elements to the snakebody
   * with the same direction as the last body element depending on the fruit value.
   */
  public void eatFruit() {
    try {
      Position lastPos = snakeBody.getLast();
      Position pos = new Position(lastPos.getX(), lastPos.getY());
      snakeBody.add(pos);
      field.occupyCell(pos, 1);
      score++;
    } catch (InvalidPositionException e) {
      e.printStackTrace();
    }
  }
  /** Snake moves one position in the direction the head is pointing to. */
  public void move() {
    if (snakeAlive) {
      field.freeCell(snakeBody.getLast());
      snakeBody.removeLast();
      Position head = getNextPosition(snakeBody.getFirst());
      snakeBody.addFirst(head);
      field.occupyCell(head, 1);
    }
  }
  /**
   * Changes the direction the snake is facing.
   *
   * @param dir The new direction the snake should face
   */
  public void setNewDirection(Direction dir) {
    direction = dir;
  }
  /**
   * Checks if the snake is still alive and that thus the game has not yet ended. Snake must not run
   * out of field or over itself to be alive
   *
   * @return true if the snake ran out of the field or ran over itself
   */
  public boolean isGameOver() {
    return snakeRanOutOfField() || snakeRanOverItself() || isMaxLength();
  }
  private boolean isMaxLength(){
    return (snakeBody.size() - 2 >= death);
  }
  /**
   * Checks if the snake is still in the valid borders of the field.
   *
   * @return true, if snake run out of the field
   */
  private boolean snakeRanOutOfField() {
    for (Position pos : snakeBody) {
      if (pos.getX() < 0 || pos.getX() >= fieldSize) {
        snakeAlive = false;
        return true;
      }
      if (pos.getY() < 0 || pos.getY() >= fieldSize) {
        snakeAlive = false;
        return true;
      }
    }
    return false;
  }
  private boolean snakeRanOverItself() {
    Position head = snakeBody.getFirst();
    for (int i = 1; i < snakeBody.size(); i++) {
      if (snakeBody.get(i).equals(head)) {
        snakeAlive = false;
        return true;
      }
    }
    return false;
  }
  /**
   * Updates a given Position of the snake according to the direction it is going.
   *
   * @param pos The actual Position to be updated
   */
  private Position getNextPosition(Position pos) {
    Position newPos = null;
    try {
      newPos = new Position(pos);
      field.occupyCell(pos, 1);
    } catch (InvalidPositionException e) {
      e.printStackTrace();
    }
    switch (direction) {
      case NORTH:
        newPos.decreaseCol();
        break;
      case SOUTH:
        newPos.increaseCol();
        break;
      case WEST:
        newPos.decreaseRow();
        break;
      case EAST:
        newPos.increaseRow();
        break;
      default:
        break;
    }
    return newPos;
  }
  public int getScore() {
    return score;
  }
}