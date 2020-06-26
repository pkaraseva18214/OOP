package ru.nsu.fit.karaseva.snake.controller;

import static ru.nsu.fit.karaseva.snake.model.Direction.EAST;
import static ru.nsu.fit.karaseva.snake.model.Direction.NORTH;
import static ru.nsu.fit.karaseva.snake.model.Direction.SOUTH;
import static ru.nsu.fit.karaseva.snake.model.Direction.WEST;
import static ru.nsu.fit.karaseva.snake.view.CellStatus.BOARD;
import static ru.nsu.fit.karaseva.snake.view.CellStatus.FRUIT;
import static ru.nsu.fit.karaseva.snake.view.CellStatus.SNAKE;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import ru.nsu.fit.karaseva.snake.model.Direction;
import ru.nsu.fit.karaseva.snake.model.Fruit;
import ru.nsu.fit.karaseva.snake.model.Snake;
import ru.nsu.fit.karaseva.snake.view.CellButton;
import ru.nsu.fit.karaseva.snake.view.SnakeGameGUI;

/**
 * The SnakeGameGUI creates the whole look of the Snake game. Moreover it is responsible for
 * handling user inputs and for playing the actual game.
 */
@SuppressWarnings({"unchecked", "rawtypes", "restriction"})
public class Controller extends Application {
  private GridPane gamePane;
  private Timeline timeline = new Timeline();
  private Direction pressedDir;
  private boolean hasGameStarted = false;
  private boolean paused;

  private Snake snake;
  private Fruit fruit;

  private int gridSize = 30;
  private final double speed = 200;

  SnakeGameGUI gui;

  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Snake");
    FlowPane root = new FlowPane(10, 10);
    root.setAlignment(Pos.BOTTOM_CENTER);
    primaryStage.setScene(new Scene(root));

    snake = new Snake(gridSize);
    fruit = new Fruit(snake);
    gui = new SnakeGameGUI(gridSize, snake, fruit);
    gamePane = gui.createGamePane();

    gui.setScoreName();
    gui.setScoreValue();
    gui.setButtons();

    gui.getBtnPlay()
        .setOnAction(
            event -> {
              if (!hasGameStarted) {
                fruit.generateRandomPosition();
                startSnakeGame();
              }
              if (paused) {
                restartGame();
                gui.getBtnPlay().setText("Start");
                gui.getBtnPause().setText("Pause");
              }
            });

    gui.getBtnPause()
        .setOnAction(
            event -> {
              if (paused) {
                timeline.play();
                gui.getBtnPause().setText("Pause");
                gui.getBtnPlay().setText("Start");
                paused = false;
              } else {
                timeline.pause();
                paused = true;
                gui.getBtnPause().setText("Continue");
                gui.getBtnPlay().setText("Restart");
              }
            });

    gui.getBtnMenu()
        .setOnAction(
            event -> {
              timeline.pause();
              paused = true;
              gui.getBtnPause().setText("Pause");
              gui.getBtnPlay().setText("Start");
              createMenuPane();
            });

    root.addEventFilter(
        KeyEvent.KEY_PRESSED,
        key -> {
          if (key.getCode() == KeyCode.W) {
            pressedDir = NORTH;
          }
          if (key.getCode() == KeyCode.A) {
            pressedDir = WEST;
          }
          if (key.getCode() == KeyCode.S) {
            pressedDir = SOUTH;
          }
          if (key.getCode() == KeyCode.D) {
            pressedDir = EAST;
          }
        });

    root.getChildren().addAll(gui.getScoreBox(), gamePane, gui.getButtonBox());
    primaryStage.show();
  }

  /** Starts the timeline for the snake game and monitors the snake action. */
  public void startSnakeGame() {
    hasGameStarted = true;
    paused = false;
    timeline =
        new Timeline(
            new KeyFrame(
                Duration.ZERO,
                new EventHandler() {
                  @Override
                  public void handle(Event event) {
                    if (pressedDir != null) {
                      snake.setNewDirection(pressedDir);
                    }
                    snake.move();
                    if (snake.snakeReachedFruit(fruit)) {
                      snakeEatsFruit();
                    }
                    if (snake.isGameOver()) {
                      timeline.stop();
                      createGameOverPane();
                    }
                    repaintPane();
                  }
                }),
            new KeyFrame(Duration.millis(speed)));

    if (snake.isSnakeAlive()) {
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
    }
  }

  private int calcIndex(int row, int col) {
    return row * gridSize + col;
  }

  private void repaintPane() {
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        CellButton cb = (CellButton) gamePane.getChildren().get(calcIndex(row, col));
        if (snake.isSnakePosition(row, col)) {
          cb.setStatus(SNAKE);
        } else {
          if (fruit.isFruitPosition(row, col)) {
            cb.setStatus(FRUIT);

          } else {
            cb.setStatus(BOARD);
          }
        }
      }
    }
  }

  private void snakeEatsFruit() {
    snake.eatFruit();
    gui.getScoreValue().setText(Integer.toString(snake.getScore()));
    fruit.generateRandomPosition();
  }

  private void restartGame() {
    snake = new Snake(gridSize);
    gui.getScoreValue().setText(Integer.toString(snake.getScore()));
    pressedDir = NORTH;
    fruit.generateRandomPosition();
    startSnakeGame();
  }

  private void createGameOverPane() {
    Stage gameOverStage = new Stage();
    BorderPane gameOverScreen = new BorderPane();
    Button restart = new Button("New Game");
    Button quit = new Button("Exit Game");
    gui.drawGameOverPane(gameOverScreen, restart, quit);

    gameOverStage.setTitle("Game Over!");
    gameOverStage.setScene(new Scene(gameOverScreen, 380, 200));
    gameOverStage.show();

    gameOverStage.setOnCloseRequest(
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
            Platform.exit();
            System.exit(0);
          }
        });

    /** Exit the game. */
    quit.setOnAction(
        (ActionEvent e) -> {
          Platform.exit();
          System.exit(0);
        });
    /** Start the new game. */
    restart.setOnAction(
        (ActionEvent e) -> {
          gameOverStage.close();
          restartGame();
        });
  }

  private void createMenuPane() {
    Stage menuStage = new Stage();
    BorderPane menuScreen = new BorderPane();
    Button restart = new Button("New Game");
    Button quit = new Button("Exit Game");
    Button help = new Button("Help");
    gui.drawMenuPane(menuScreen, restart, quit, help);

    menuStage.setTitle("Menu");
    menuStage.setScene(new Scene(menuScreen, 380, 200));
    menuStage.show();

    menuStage.setOnCloseRequest(
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
            Platform.exit();
            System.exit(0);
          }
        });

    /** Exit the game. */
    quit.setOnAction(
        (ActionEvent e) -> {
          Platform.exit();
          System.exit(0);
        });
    /** Start the new game. */
    restart.setOnAction(
        (ActionEvent e) -> {
          menuStage.close();
          restartGame();
        });

    help.setOnAction(
        (ActionEvent e) -> {
          menuStage.close();
          gui.createHelpPane();
        });
  }
}
