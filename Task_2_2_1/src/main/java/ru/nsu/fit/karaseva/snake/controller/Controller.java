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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.fit.karaseva.snake.model.Direction;
import ru.nsu.fit.karaseva.snake.model.Field;
import ru.nsu.fit.karaseva.snake.model.Fruit;
import ru.nsu.fit.karaseva.snake.model.InvalidPositionException;
import ru.nsu.fit.karaseva.snake.model.Snake;
import ru.nsu.fit.karaseva.snake.view.CellButton;

/**
 * The SnakeGameGUI creates the whole look of the Snake game. Moreover it is responsible for
 * handling user inputs and for playing the actual game.
 */
public class Controller {

  private Label scoreValue;
  private GridPane gamePane;
  private Timeline timeline;
  private Direction pressedDir;
  private boolean hasGameStarted;
  private boolean paused;
  private Snake snake;
  private Fruit fruit;
  private int gridSize;
  private final double speed;
  private Field field;

  public Controller(
      Label scoreValue,
      GridPane gamePane,
      Timeline timeline,
      boolean hasGameStarted,
      Snake snake,
      Fruit fruit,
      int gridSize,
      double speed,
      Field field) {
    this.scoreValue = scoreValue;
    this.gamePane = gamePane;
    this.timeline = timeline;
    this.hasGameStarted = hasGameStarted;
    this.snake = snake;
    this.fruit = fruit;
    this.gridSize = gridSize;
    this.speed = speed;
    this.field = field;
  }

  public void start(FlowPane root, Button btnPlay, Button btnPause, Button btnMenu) {

    btnPlay.setOnAction(
        event -> {
          if (!hasGameStarted) {
            try {
              fruit.generateRandomPosition();
            } catch (InvalidPositionException e) {
              e.printStackTrace();
            }
            startSnakeGame();
          }
          if (paused) {
            try {
              restartGame();
            } catch (InvalidPositionException e) {
              e.printStackTrace();
            }
            btnPlay.setText("Start");
            btnPause.setText("Pause");
          }
        });
    btnPause.setOnAction(
        event -> {
          if (paused) {
            timeline.play();
            btnPause.setText("Pause");
            btnPlay.setText("Start");
            paused = false;
          } else {
            timeline.pause();
            paused = true;
            btnPause.setText("Continue");
            btnPlay.setText("Restart");
          }
        });
    btnMenu.setOnAction(
        event -> {
          timeline.pause();
          paused = true;
          btnPause.setText("Pause");
          btnPlay.setText("Start");
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
                      try {
                        snakeEatsFruit();
                      } catch (InvalidPositionException e) {
                        e.printStackTrace();
                      }
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

  private void snakeEatsFruit() throws InvalidPositionException {
    snake.eatFruit();
    scoreValue.setText(Integer.toString(snake.getScore()));
    field.freeCell(fruit.getFruitPosition());
    fruit.generateRandomPosition();
  }

  private void restartGame() throws InvalidPositionException {
    snake = new Snake(gridSize, field);
    scoreValue.setText(Integer.toString(snake.getScore()));
    pressedDir = NORTH;
    field.freeCell(fruit.getFruitPosition());
    fruit.generateRandomPosition();
    startSnakeGame();
  }

  private void createGameOverPane() {
    Stage gameOverStage = new Stage();
    BorderPane gameOverScreen = new BorderPane();
    gameOverScreen.setPrefHeight(500);
    gameOverScreen.setPrefWidth(500);
    Label gameOver = new Label("Game Over!");
    gameOver.setTextFill(Color.BLACK);
    gameOver.setFont(Font.font("Courier New", FontWeight.BOLD, 60));
    gameOverScreen.setCenter(gameOver);
    gameOverScreen.setPadding(new Insets(0, 0, 10, 0));
    Button restart = new Button("New Game");
    Button quit = new Button("Exit Game");
    HBox buttonBoxGameOver = new HBox(3.0);
    buttonBoxGameOver.getChildren().addAll(restart, quit);
    gameOverScreen.setBottom(buttonBoxGameOver);
    buttonBoxGameOver.setAlignment(Pos.BOTTOM_CENTER);
    gameOverStage.setTitle("Game Over!");
    gameOverStage.setScene(new Scene(gameOverScreen, 380, 200));
    gameOverStage.show();
    gameOverStage.setOnCloseRequest(
        we -> {
          Platform.exit();
          System.exit(0);
        });
    quit.setOnAction(
        (ActionEvent e) -> {
          Platform.exit();
          System.exit(0);
        });
    restart.setOnAction(
        (ActionEvent e) -> {
          gameOverStage.close();
          try {
            restartGame();
          } catch (InvalidPositionException ex) {
            ex.printStackTrace();
          }
        });
  }

  private void createMenuPane() {
    Stage menuStage = new Stage();
    BorderPane menuScreen = new BorderPane();
    menuScreen.setPrefHeight(500);
    menuScreen.setPrefWidth(500);
    Label gameOver = new Label("Menu");
    gameOver.setTextFill(Color.BLACK);
    gameOver.setFont(Font.font("Courier New", FontWeight.BOLD, 60));
    menuScreen.setCenter(gameOver);
    menuScreen.setPadding(new Insets(0, 0, 10, 0));
    Button restart = new Button("New Game");
    Button quit = new Button("Exit Game");
    Button help = new Button("Help");
    VBox buttonBoxGameOver = new VBox(3.0);
    buttonBoxGameOver.getChildren().addAll(restart, quit, help);
    menuScreen.setBottom(buttonBoxGameOver);
    buttonBoxGameOver.setAlignment(Pos.BOTTOM_CENTER);
    menuStage.setTitle("Menu");
    menuStage.setScene(new Scene(menuScreen, 380, 200));
    menuStage.show();
    menuStage.setOnCloseRequest(
        we -> {
          Platform.exit();
          System.exit(0);
        });
    quit.setOnAction(
        (ActionEvent e) -> {
          Platform.exit();
          System.exit(0);
        });
    restart.setOnAction(
        (ActionEvent e) -> {
          menuStage.close();
          try {
            restartGame();
          } catch (InvalidPositionException ex) {
            ex.printStackTrace();
          }
        });
    help.setOnAction(
        (ActionEvent e) -> {
          menuStage.close();
          createHelpPane();
        });
  }

  public void createHelpPane() {
    Stage helpStage = new Stage();
    BorderPane helpScreen = new BorderPane();
    helpScreen.setPrefHeight(500);
    helpScreen.setPrefWidth(500);
    Label helper = new Label("Help");
    helper.setTextFill(Color.BLACK);
    helper.setFont(Font.font("Courier New", FontWeight.BOLD, 30));
    helpScreen.setCenter(helper);
    helpScreen.setPadding(new Insets(0, 0, 10, 0));
    VBox help = new VBox(3.0);
    Text text1 = new Text("Press W  -  move up");
    Text text2 = new Text("Press S  -  move down");
    Text text3 = new Text("Press A  -  move left");
    Text text4 = new Text("Press D  -  move right");
    help.getChildren().addAll(text1, text2, text3, text4);
    helpScreen.setBottom(help);
    help.setAlignment(Pos.BOTTOM_CENTER);
    helpStage.setTitle("Settings");
    helpStage.setScene(new Scene(helpScreen, 380, 200));
    helpStage.show();
  }
}
