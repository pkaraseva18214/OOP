package ru.nsu.fit.karaseva.snake.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.nsu.fit.karaseva.snake.model.Fruit;
import ru.nsu.fit.karaseva.snake.model.Snake;

/** The SnakeGameGUI creates the whole look of the Snake game. */
@SuppressWarnings({"unchecked", "rawtypes", "restriction"})
public class SnakeGameGUI {
  private int gridSize;
  private Label scoreValue;
  private Label scoreName;
  private HBox scoreBox;
  private Button btnPlay;
  private HBox buttonBox;
  private Button btnPause;
  private Button btnMenu;
  private Snake snake;
  private Fruit fruit;

  private final Font fatFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
  private final Font mediumFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
  private final Font smallFont = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);

  public SnakeGameGUI(int gridSize, Snake snake, Fruit fruit) {
    this.gridSize = gridSize;
    this.snake = snake;
    this.fruit = fruit;
    scoreValue = new Label();
    scoreName = new Label("Score: ");
    scoreBox = new HBox(3.0);
    btnPlay = new Button("Start");
    buttonBox = new HBox(8.0);
    btnPause = new Button("Pause");
    btnMenu = new Button("Menu");
  }

  public Label getScoreValue() {
    return scoreValue;
  }

  public HBox getScoreBox() {
    return scoreBox;
  }

  public Button getBtnPlay() {
    return btnPlay;
  }

  public HBox getButtonBox() {
    return buttonBox;
  }

  public Button getBtnPause() {
    return btnPause;
  }

  public Button getBtnMenu() {
    return btnMenu;
  }

  /**
   * Sets score name.
   */
  public void setScoreName() {
    scoreName.setMinWidth(60);
    scoreName.setMinHeight(20);
    scoreName.setFont(fatFont);
  }

  /**
   * Sets score value at the beginning of the game.
   */
  public void setScoreValue() {
    scoreValue.setText("0");
    scoreValue.setMinWidth(60);
    scoreValue.setMinHeight(20);
    scoreValue.setFont(fatFont);
  }

  /**
   * Sets buttons at the beginning of the game.
   */
  public void setButtons() {
    btnPlay.setFont(smallFont);
    btnPause.setFont(mediumFont);
    btnMenu.setFont(smallFont);

    btnPlay.setMinWidth(100);
    btnPlay.setMinHeight(60);
    btnPause.setMinWidth(100);
    btnPause.setMinHeight(60);
    btnMenu.setMinWidth(100);
    btnMenu.setMinHeight(60);

    scoreBox.getChildren().addAll(scoreName, scoreValue);
    HBox.setMargin(scoreName, new Insets(10, 0, 0, 0));
    HBox.setMargin(scoreValue, new Insets(10, 0, 0, 0));

    buttonBox.getChildren().addAll(btnPlay, btnPause, btnMenu);
    HBox.setMargin(btnPlay, new Insets(0, 0, 10, 0));
  }

  /**
   * Creates Game pane.
   * @return
   */
  public GridPane createGamePane() {
    GridPane pane = new GridPane();
    for (int row = 0; row < gridSize; row++) {
      for (int col = 0; col < gridSize; col++) {
        pane.add(new CellButton(), row, col);
      }
    }
    return pane;
  }

  /**
   * Creates game over pane.
   */
  public void drawGameOverPane(BorderPane gameOverScreen, Button restart, Button quit) {
    gameOverScreen.setPrefHeight(500);
    gameOverScreen.setPrefWidth(500);
    Label gameOver = new Label("Game Over!");
    gameOver.setTextFill(Color.BLACK);
    gameOver.setFont(Font.font("Courier New", FontWeight.BOLD, 60));
    gameOverScreen.setCenter(gameOver);
    gameOverScreen.setPadding(new Insets(0, 0, 10, 0));

    HBox buttonBoxGameOver = new HBox(3.0);
    buttonBoxGameOver.getChildren().addAll(restart, quit);
    gameOverScreen.setBottom(buttonBoxGameOver);
    buttonBoxGameOver.setAlignment(Pos.BOTTOM_CENTER);
  }

  /**
   * Creates Menu pane.
   */
  public void drawMenuPane(BorderPane menuScreen, Button restart, Button quit, Button help) {
    menuScreen.setPrefHeight(500);
    menuScreen.setPrefWidth(500);
    Label gameOver = new Label("Menu");
    gameOver.setTextFill(Color.BLACK);
    gameOver.setFont(Font.font("Courier New", FontWeight.BOLD, 60));
    menuScreen.setCenter(gameOver);
    menuScreen.setPadding(new Insets(0, 0, 10, 0));

    VBox buttonBoxGameOver = new VBox(3.0);
    buttonBoxGameOver.getChildren().addAll(restart, quit, help);
    menuScreen.setBottom(buttonBoxGameOver);
    buttonBoxGameOver.setAlignment(Pos.BOTTOM_CENTER);
  }

  /**
   * Creates Help pane with instructions how to play.
   */
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
