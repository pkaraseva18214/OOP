package ru.nsu.fit.karaseva.snake.model;

public class InvalidSnakePositionException extends Exception {

  private static final long serialVersionUID = 1L;

  public InvalidSnakePositionException(String message) {
    super(message);
  }
}
