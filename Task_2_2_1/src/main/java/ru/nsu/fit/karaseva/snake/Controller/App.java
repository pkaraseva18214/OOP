package ru.nsu.fit.karaseva.snake.controller;

import javafx.application.Application;
import ru.nsu.fit.karaseva.snake.view.SnakeGameGUI;

@SuppressWarnings("restriction")
public class App {
  public static void main(String[] args) {
    Application.launch(SnakeGameGUI.class, args);
  }
}