package ru.nsu.fit.karaseva.snake.model;

public class InvalidPositionException extends Exception {

  private static final long serialVersionUID = 1L;

  public InvalidPositionException(String message) {
    super(message);
  }
}
