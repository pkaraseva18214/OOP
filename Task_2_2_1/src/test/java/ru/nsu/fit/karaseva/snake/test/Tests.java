package ru.nsu.fit.karaseva.snake.test;

import org.junit.Assert;
import ru.nsu.fit.karaseva.snake.model.Field;
import ru.nsu.fit.karaseva.snake.model.Fruit;
import ru.nsu.fit.karaseva.snake.model.InvalidPositionException;
import ru.nsu.fit.karaseva.snake.model.Position;
import ru.nsu.fit.karaseva.snake.model.Snake;

import org.junit.Test;

public class Tests {

  @Test
  public void snakeMovementTest() throws InvalidPositionException {
    int gridSize = 30;
    Field field = new Field(gridSize);
    Snake snake = new Snake(gridSize, field);
    Assert.assertTrue(snake.isSnakePosition(gridSize / 2, gridSize / 2 + 1));
    Assert.assertTrue(snake.isSnakePosition(gridSize / 2, gridSize / 2));
    Assert.assertFalse(field.isCellEmpty(new Position(gridSize / 2, gridSize / 2)));
    Assert.assertFalse(field.isCellEmpty(new Position(gridSize / 2, gridSize / 2 + 1)));
    snake.move();
    snake.move();
    Assert.assertTrue(snake.isSnakePosition(gridSize / 2, gridSize / 2 - 1));
    Assert.assertFalse(field.isCellEmpty(new Position(gridSize / 2, gridSize / 2 - 1)));
    Assert.assertTrue(field.isCellEmpty(new Position(gridSize / 2, gridSize / 2 + 1)));
    snake.move();
    Assert.assertTrue(snake.isSnakePosition(gridSize / 2, gridSize / 2 - 2));
    Assert.assertFalse(field.isCellEmpty(new Position(gridSize / 2, gridSize / 2 - 2)));
    Assert.assertFalse(field.isCellEmpty(new Position(gridSize / 2, gridSize / 2 - 1)));
    Assert.assertTrue(field.isCellEmpty(new Position(gridSize / 2, gridSize / 2)));
  }

  @Test
  public void fruitPositionTest() throws InvalidPositionException {
    int gridSize = 30;
    Field field = new Field(gridSize);
    Fruit fruit = new Fruit(field);
    fruit.generateRandomPosition();
    Position pos1 = fruit.getFruitPosition();
    Assert.assertTrue(fruit.isFruitPosition(pos1.getX(), pos1.getY()));
    Assert.assertFalse(field.isCellEmpty(pos1));
    fruit.generateRandomPosition();
    Position pos2 = fruit.getFruitPosition();
    Assert.assertTrue(fruit.isFruitPosition(pos2.getX(), pos2.getY()));
    Assert.assertFalse(field.isCellEmpty(pos2));
  }

  @Test
  public void snakeReachesFruit() throws InvalidPositionException {
    int gridSize = 30;
    Field field = new Field(gridSize);
    Snake snake = new Snake(gridSize, field);
    Fruit fruit = new Fruit(field);
    fruit.setFruitPosition(new Position(gridSize / 2, gridSize / 2 - 1));
    Position p = fruit.getFruitPosition();
    snake.move();
    snake.move();
    Assert.assertTrue(snake.snakeReachedFruit(fruit));
    Assert.assertTrue(snake.isSnakeAlive());
  }
}
