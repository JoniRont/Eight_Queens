package Chessboard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Paranneltu bruteforce menetelmä etsimään kaikki vaihtoehdot
 * asettaa kuningattaret pelilaudalle uhkaamatta toisiaan
 * pelilaudan kokoa voi muuttaa(minimi 4), maksimi ei ole mutta luonnollisesti
 * mitä isompi pelilauta == tarvitaan tehoja koneesta
 * oletuksena on kahdeksan jolloin pitäisi löytyä 92 eri vaihtoehtoa
 * 
 * https://www.datagenetics.com/blog/august42012/   
 * Sivulta voi tarkistaa toimiiko algoritmi sekä löytyy kaikki eri vaihtoehdot
 *  pelilaudan  ratkaisuiden määrä
 *  koko
 *  1 x 1 	    1 	  
 *  2 x 2 	    0 	 
 *  3 x 3 	    0 	  
 *  4 x 4 	    2 	 
 *  5 x 5 	    10 	 
 *  6 x 6 	    4 	  
 *  7 x 7 	    40 	 
 *  8 x 8 	    92 	 
 *  9 x 9 	    352 	 
 *  10 x 10 	  724 	 
 *  11 x 11 	  2,680 	 
 *  12 x 12 	  14,200 	 
 *  13 x 13 	  73,712 	 
 *  14 x 14 	  365,596 	 
 *
 */
public class EightQueens {
  public static void main(String[] args) {
    // Pelikentän koon ja aloituspisteen asettaminen!
    // -------------------------------------------------------------
    int chessBoardSize = 7;
    // -------------------------------------------------------------
    if(chessBoardSize<4)chessBoardSize=4;
    QueenSolver s = new QueenSolver(chessBoardSize);
    s.startGame();
  }
}

class QueenSolver {
  private int size;

  public QueenSolver(int boardSize) {
    size = boardSize;
  }

  public void printer(int[][] board) {
    System.out.println(Arrays.deepToString(board));
  }
 
  public void startGame() {
    ArrayList<Integer> tempPath = new ArrayList<Integer>();
    int[][] board = new int[this.size][this.size];
    ArrayList<int[][]> allPaths = new ArrayList<int[][]>();
    ArrayList<ArrayList<Integer>> allPathsInList = new ArrayList<ArrayList<Integer>> ();
    int col = 0;
    int counter = 0;
    int startRow = 0;
    int addedQuuens = 0;
    int flawColumn = 0;
    int lastGoodPoint = -1;
    
    
    while (true) {
      if(col==0&&startRow>=this.size-1)break;
      flawColumn=0;
      counter=0;
      for (int row = startRow; row < this.size;row++) {
        if(counter>=this.size)break;
        if (isPossible(col, row, board)&&row !=lastGoodPoint&&!tempPath.contains(row)) {
          addedQuuens += 1;
          board[col][row] = 8;
          tempPath.add(row);
          col += 1;
          lastGoodPoint = row;
          startRow = 0;
          break;
        } 
        counter += 1;
        flawColumn +=1;
        board[col][row] = 0;//testissä oli 1 jotta pysty seuraa paikoitusta debuggauksessa
      }
      if (flawColumn >= this.size||(flawColumn+startRow)>=this.size||col>=this.size) {
        if(addedQuuens==this.size){
          ArrayList<Integer> tempArrayPath = new ArrayList<Integer>(tempPath);
          allPaths.add(board);
          allPathsInList.add(tempArrayPath);
        }
        counter=0;
        addedQuuens-=1;
        lastGoodPoint=tempPath.get(tempPath.size()-1);
        startRow = lastGoodPoint;
        col=col-1;
        for (int i = col; i < board.length; i++) {
          for (int j = 0; j < board.length; j++) {
            board[i][j] = 0;
          }
        }
        tempPath.remove(tempPath.size()-1);
      }
    }
    
    ArrayList<ArrayList<Integer>> listWithoutDuplicates = new ArrayList<>(new HashSet<>(allPathsInList));
    System.out.println("vastauksia löytyi: "+listWithoutDuplicates.size() );
  }

  private boolean isPossible(int posCol, int posRow, int[][] queensOnBoard) {
    int tempRow = posRow;
    // tarkistetaan taakse
    for (int i = posCol; i >= 0; i--) {
      if (queensOnBoard[i][posRow] == 8) {
        return false;
      }
    }

    // tarkistetaan vasen yläviisto
    for (int i = posCol; i >= 0; i--) {
      if (tempRow < 0) {
        break;
      }

      if (queensOnBoard[i][tempRow] == 8) {
        return false;
      }
      tempRow -= 1;
    }

    tempRow = posRow;
    // tarkistetaan vasen alaviisto
    for (int i = posCol; i >= 0; i--) {
      if (tempRow >= queensOnBoard.length) {
        break;
      }
      if (queensOnBoard[i][tempRow] == 8) {
        return false;
      }
      tempRow += 1;
    }
    return true;
  }
}