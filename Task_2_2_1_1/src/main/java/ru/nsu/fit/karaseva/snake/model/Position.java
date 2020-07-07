package ru.nsu.fit.karaseva.snake.model;

/**
 * The Position allows to define a positive location on a grid Objects like Snake and Fruit have a
 * certain position that can be represented with this class.
 */
public class Position {
  private int row;
  private int col;

  /**
   * Constructor to make a position with default direction being NORTH.
   *
   * @param row The row or x value of the position
   * @param col The col or y value of the position
   * @throws InvalidPositionException when row or col are negative
   */
  public Position(int row, int col) throws InvalidPositionException {
    if (row >= 0 && col >= 0) {
      this.row = row;
      this.col = col;
    } else {
      throw new InvalidPositionException(
          String.format("Value x{%s} and y{%s} must be greater than 0", row, col));
    }
  }
  /**
   * Creates a position out of a given position with the same values.
   *
   * @param pos The position from which to take the values for a new position
   * @throws InvalidPositionException when row or col are negative
   */
  public Position(Position pos) throws InvalidPositionException {
    this(pos.getX(), pos.getY());
  }

  public int getX() {
    return row;
  }

  public int getY() {
    return col;
  }

  public void increaseRow() {
    row++;
  }

  public void increaseCol() {
    col++;
  }

  public void decreaseRow() {
    row--;
  }

  public void decreaseCol() {
    col--;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Position)) {
      return false;
    }
    Position pos = (Position) o;
    return pos.getX() == row && pos.getY() == col;
  }
}
