package ru.nsu.fit.karaseva.snake.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import ru.nsu.fit.karaseva.snake.controller.Controller;
import ru.nsu.fit.karaseva.snake.model.Field;
import ru.nsu.fit.karaseva.snake.model.Fruit;
import ru.nsu.fit.karaseva.snake.model.InvalidPositionException;
import ru.nsu.fit.karaseva.snake.model.Snake;
/**
 * The SnakeGameGUI creates the whole look of the Snake game. Moreover it is responsible for
 * handling user inputs and for playing the actual game.
 */
@SuppressWarnings({"unchecked", "rawtypes", "restriction"})
public class SnakeGameGUI extends Application {
  private Timeline timeline = new Timeline();
  private int gridSize = 30;
  private final Font fatFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
  private final Font mediumFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
  private final Font smallFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);

  @Override
  public void start(Stage primaryStage) throws InvalidPositionException {
    primaryStage.setTitle("Snake");
    FlowPane root = new FlowPane(10, 10);
    root.setAlignment(Pos.BOTTOM_CENTER);
    primaryStage.setScene(new Scene(root));
    Field field = new Field(gridSize);
    Snake snake = new Snake(gridSize, field);
    Fruit fruit = new Fruit(field);
    GridPane gamePane = createGamePane();
    Label scoreName = new Label("Score: ");
    scoreName.setMinWidth(60);
    scoreName.setMinHeight(20);
    scoreName.setFont(fatFont);
    Label scoreValue = new Label();
    scoreValue.setText(Integer.toString(snake.getScore()));
    scoreValue.setMinWidth(60);
    scoreValue.setMinHeight(20);
    scoreValue.setFont(fatFont);
    Button btnPlay = new Button("Start");
    btnPlay.setFont(smallFont);
    Button btnPause = new Button("Pause");
    btnPause.setFont(mediumFont);
    Button btnMenu = new Button("Menu");
    btnMenu.setFont(smallFont);
    btnPlay.setMinWidth(100);
    btnPlay.setMinHeight(60);
    btnPause.setMinWidth(100);
    btnPause.setMinHeight(60);
    btnMenu.setMinWidth(100);
    btnMenu.setMinHeight(60);
    HBox scoreBox = new HBox(3.0);
    scoreBox.getChildren().addAll(scoreName, scoreValue);
    HBox.setMargin(scoreName, new Insets(10, 0, 0, 0));
    HBox.setMargin(scoreValue, new Insets(10, 0, 0, 0));
    HBox buttonBox = new HBox(8.0);
    buttonBox.getChildren().addAll(btnPlay, btnPause, btnMenu);
    HBox.setMargin(btnPlay, new Insets(0, 0, 10, 0));

    boolean hasGameStarted = false;
    double speed = 200;
    Controller controller = new Controller(
        scoreValue,
        gamePane,
        timeline,
        hasGameStarted,
        snake,
        fruit,
        gridSize,
        speed,
        field);

    controller.start(root, btnPlay, btnPause, btnMenu);

    root.getChildren().addAll(scoreBox, gamePane, buttonBox);
    primaryStage.show();
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
}
