package ru.nsu.fit.karaseva.snake;

import static ru.nsu.fit.karaseva.snake.Direction.EAST;
import static ru.nsu.fit.karaseva.snake.Direction.NORTH;
import static ru.nsu.fit.karaseva.snake.Direction.SOUTH;
import static ru.nsu.fit.karaseva.snake.Direction.WEST;
import static ru.nsu.fit.karaseva.snake.CellStatus.BOARD;
import static ru.nsu.fit.karaseva.snake.CellStatus.FRUIT;
import static ru.nsu.fit.karaseva.snake.CellStatus.SNAKE;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * The SnakeGameGUI creates the whole look of the Snake game. Moreover it is responsible for
 * handling user inputs and for playing the actual game.
 */
@SuppressWarnings({"unchecked", "rawtypes", "restriction"})
public class SnakeGameGUI extends Application {

  private HBox buttonBox;
  private Button btnPlay;
  private Button btnPause;

  private HBox scoreBox;
  private Label scoreName;
  private Label scoreValue;

  private GridPane gamePane;
  private Timeline timeline = new Timeline();
  private Direction pressedDir;
  private boolean hasGameStarted = false;
  private boolean paused;

  private Snake snake;
  private Fruit fruit;

  private int gridSize = 30;
  private final double speed = 250;

  private final Font fatFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
  private final Font mediumFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
  private final Font smallFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);

  @Override
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Snake");
    FlowPane root = new FlowPane(10, 10);
    root.setAlignment(Pos.BOTTOM_CENTER);
    primaryStage.setScene(new Scene(root));

    snake = new Snake(gridSize);
    fruit = new Fruit(snake);
    gamePane = createGamePane();

    scoreName = new Label("Score: ");
    scoreName.setMinWidth(60);
    scoreName.setMinHeight(20);
    scoreName.setFont(fatFont);
    scoreValue = new Label();
    scoreValue.setText(Integer.toString(snake.getScore()));
    scoreValue.setMinWidth(60);
    scoreValue.setMinHeight(20);
    scoreValue.setFont(fatFont);

    btnPlay = new Button("Start");
    btnPlay.setFont(smallFont);
    btnPause = new Button("II");
    btnPause.setFont(mediumFont);

    btnPlay.setMinWidth(100);
    btnPlay.setMinHeight(60);
    btnPause.setMinWidth(100);
    btnPause.setMinHeight(60);

    scoreBox = new HBox(3.0);
    scoreBox.getChildren().addAll(scoreName, scoreValue);
    HBox.setMargin(scoreName, new Insets(10, 0, 0, 0));
    HBox.setMargin(scoreValue, new Insets(10, 0, 0, 0));

    buttonBox = new HBox(8.0);
    buttonBox.getChildren().addAll(btnPlay, btnPause);
    HBox.setMargin(btnPlay, new Insets(0, 0, 10, 0));

    btnPlay.setOnAction(
        event -> {
          if (!hasGameStarted) {
            fruit.generateRandomPosition();
            startSnakeGame();
          }
          if (paused) {
            restartGame();
            btnPlay.setText("Start");
            btnPause.setText("II");
          }
        });

    btnPause.setOnAction(
        event -> {
          if (paused) {
            timeline.play();
            btnPause.setText("II");
            btnPlay.setText("Start");
            paused = false;
          } else {
            timeline.pause();
            paused = true;
            btnPause.setText(">");
            btnPlay.setText("Restart");
          }
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

    root.getChildren().addAll(scoreBox, gamePane, buttonBox);
    primaryStage.show();
  }

  public void setSizeOfGrid(int size){
    this.gridSize = size;
  }

  /** Starts the timeline for the snake game and monitors the snake action. */
  private void startSnakeGame() {
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

  private GridPane createGamePane() {
    GridPane pane = new GridPane();
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        pane.add(new CellButton(), row, col);
      }
    }
    return pane;
  }

  private void snakeEatsFruit() {
    snake.eatFruit(fruit);
    scoreValue.setText(Integer.toString(snake.getScore()));
    fruit.generateRandomPosition();
  }

  private void restartGame() {
    snake = new Snake(gridSize);
    scoreValue.setText(Integer.toString(snake.getScore()));
    pressedDir = NORTH;
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
}
