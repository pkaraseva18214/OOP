package ru.nsu.fit.karaseva.snake.model;

/**
 * Class represents game field (to be precise the game grid). Empty cell has value as 0,
 * the cell in which the snake or fruit is located has non-zero value.
 */
public class Field {

  private int[][] field = new int[31][31];           //1 - snake
  private int gridSize;                              //2 - fruit

  public Field(int gridSize){
    this.gridSize = gridSize;
    for (int i = 1; i<30; i++){
      for (int j = 1; j < 30; j++){
        field[i][j] = 0;
      }
    }
  }

  /**
   * @return gridSize
   */
  public int getFieldSize(){
    return gridSize;
  }

  /**
   * Checks if the cell is empty.
   * @param pos position of the cell.
   * @return true if empty, otherwise false.
   */
  public boolean isCellEmpty(Position pos) {
    return field[pos.getX()][pos.getY()] == 0;
  }

  /**
   * Occupies cell if snake or fruit located here.
   * @param pos position of the cell.
   * @param whoIsThis - parameter that says who is in this cell.
   */
  public void occupyCell(Position pos, int whoIsThis) {
    field[pos.getX()][pos.getY()] = whoIsThis;
  }

  /**
   * Frees sell if there is nothing here.
   * @param pos position of the cell.
   */
  public void freeCell(Position pos){
    field[pos.getX()][pos.getY()] = 0;
  }

}
