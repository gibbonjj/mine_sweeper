// Name: James Gibbons
// USC NetID: gibbonsj
// CS 455 PA3
// Fall 2018
import java.util.Random;

/** 
   MineField
      class with locations of mines for a game.
      This class is mutable, because we sometimes need to change it once it's created.
      mutators: populateMineField, resetEmpty
      includes convenience method to tell the number of mines adjacent to a location.
 */
public class MineField {
   
   private boolean[][] mineData;
   private int numRow;
   private int numCol;
   private int numMines;
   private Random random;
   
   /**
      Create a minefield with same dimensions as the given array, and populate it with the mines in the array
      such that if mineData[row][col] is true, then hasMine(row,col) will be true and vice versa.  numMines() for
      this minefield will corresponds to the number of 'true' values in mineData.
    * @param mineData  the data for the mines; must have at least one row and one col.
    */
   public MineField(boolean[][] mineData) {

      assert mineData.length > 0 && mineData[0].length > 0;

      random = new Random();
      this.mineData = mineData;
      numRow = mineData.length;
      numCol = mineData[0].length;

      int mineCounter = 0;
      for (int i = 0; i < numRow; i++) {
        for (int j = 0; j < numCol; j++) {
          if (hasMine(i,j)) {
            mineCounter++;
          }
        }
      }

      numMines = mineCounter;
   }
   
   /**
      Create an empty minefield (i.e. no mines anywhere), that may later have numMines mines (once 
      populateMineField is called on this object).  Until populateMineField is called on such a MineField, 
      numMines() will not correspond to the number of mines currently in the MineField.
      @param numRows  number of rows this minefield will have, must be positive
      @param numCols  number of columns this minefield will have, must be positive
      @param numMines   number of mines this minefield will have,  once we populate it.
      PRE: numRows > 0 and numCols > 0 and 0 <= numMines < (1/3 of total number of field locations). 
    */
   public MineField(int numRows, int numCols, int numMines) {
      assert numRows > 0 && numCols > 0;
      int limit = numRows * numCols; 
      assert numMines < limit / 3.0;

      numRow = numRows;
      numCol = numCols;
      random = new Random();
      mineData = new boolean[numRows][numCols];
      this.numMines = numMines;

      resetEmpty();
   }

   /**
      Removes any current mines on the minefield, and puts numMines() mines in random locations on the minefield,
      ensuring that no mine is placed at (row, col).
      @param row the row of the location to avoid placing a mine
      @param col the column of the location to avoid placing a mine
      PRE: inRange(row, col)
    */
   public void populateMineField(int row, int col) {
    assert inRange(row, col);

    resetEmpty();  

    for (int i = 1; i <= numMines; i++) {
      int rowFill = random.nextInt(numRow);
      int colFill = random.nextInt(numCol);
      while (row == rowFill && col == colFill || mineData[rowFill][colFill] == true) {
        rowFill = random.nextInt(numRow);
        colFill = random.nextInt(numCol);
      }
      mineData[rowFill][colFill] = true;
    }
   }
   
   /**
      Reset the minefield to all empty squares.  This does not affect numMines(), numRows() or numCols()
      Thus, after this call, the actual number of mines in the minefield does not match numMines().  
      Note: This is the state the minefield is in at the beginning of a game.
    */
   public void resetEmpty() {
      for (int i = 0; i < numRow; i++) {
        for (int j = 0; j < numCol; j++) {
          mineData[i][j] = false;
        }
      }
   }

  /**
     Returns the number of mines adjacent to the specified mine location (not counting a possible 
     mine at (row, col) itself).
     Diagonals are also considered adjacent, so the return value will be in the range [0,8]
     @param row  row of the location to check
     @param col  column of the location to check
     @return  the number of mines adjacent to the square at (row, col)
     PRE: inRange(row, col)
   */
   public int numAdjacentMines(int row, int col) {
    assert inRange(row, col);

      int count = 0;
      for (int i = row - 1; i <= row + 1; i++) {
        for (int j = col - 1; j <= col + 1; j++) {
          if (hasMine(i, j)) {
            count++;
          }
        }
      }
      return count;       
   }
   
   /**
      Returns true iff (row,col) is a valid field location.  Row numbers and column numbers
      start from 0.
      @param row  row of the location to consider
      @param col  column of the location to consider
      @return whether (row, col) is a valid field location
   */
   public boolean inRange(int row, int col) {

      if (col < numCol && col >= 0 && row < numRow && row >= 0) {return true;}
      else {return false;}      
   }
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */  
   public int numRows() {
      return numRow;      
   }
   
   /**
      Returns the number of rows in the field.
      @return number of rows in the field
   */    
   public int numCols() {
      return numCol;      
   }
   
   /**
      Returns whether there is a mine in this square
      @param row  row of the location to check
      @param col  column of the location to check
      @return whether there is a mine in this square
      PRE: inRange(row, col)   
   */    
   public boolean hasMine(int row, int col) {

      if (inRange(row, col)) {
        if (mineData[row][col] == true) {return true;}
        else {return false;}
      }
      else { return false;}
   }

   /**
      Returns the number of mines you can have in this minefield.  For mines created with the 3-arg constructor,
      some of the time this value does not match the actual number of mines currently on the field.  See doc for that
      constructor, resetEmpty, and populateMineField for more details.
    * @return number of mines
    */
   public int numMines() {
     return numMines;      
   }

   /**
    Returns a string of booleans of mine placement for the mineField
    @return boolean string of mines
   */
   public String toString() {
    String string = "";
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        string += " " + mineData[i][j];
      }
    }
    return string;
   }      
}

