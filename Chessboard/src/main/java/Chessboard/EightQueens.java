package Chessboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** HUOM!!! värit eivät toimi kaikissa terminaaleissa (powershell/windows komentokehoite), vscode terminaali sen sijaan toimii,
 * mikäli tämä häiritsee tulee kommentoida/poistaa printer() metodista kaikki ANSI_<color> rivit.
 * 
 * Paranneltu bruteforce/backtracking menetelmä etsimään kaikki vaihtoehdot asettaa
 * kuningattaret pelilaudalle uhkaamatta toisiaan: Pelilaudan kokoa voi
 * muuttaa(minimi 4), maksimi ei ole mutta luonnollisesti mitä isompi pelilauta
 * == tarvitaan tehoja koneesta oletuksena on kahdeksan jolloin pitäisi löytyä
 * 92 eri vaihtoehtoa
 * 
 * https://www.datagenetics.com/blog/august42012/ Sivulta voi tarkistaa toimiiko
 * algoritmi sekä löytyy kaikki eri vaihtoehdot pelilaudan ratkaisuiden määrä
 * koko 
 * 1 x 1 1 
 * 2 x 2 0 
 * 3 x 3 0 
 * 4 x 4 2 
 * 5 x 5 10 
 * 6 x 6 4 
 * 7 x 7 40 
 * 8 x 8 92 
 * 9 x 9 352 
 * 10 x 10 724 
 * 11 x 11 2,680 
 * 12 x 12 14,200 
 * 13 x 13 73,712 
 * 14 x 14 365,596
 */
public class EightQueens {
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_RESET = "\u001B[0m";
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    // Pelikentän koon alustaminen! sekä eri tulostuksien määrän asettaminen
    // -------------------------------------------------------------
    int chessBoardSize=4;
    int printValue=1;
    try {
      System.out.print("Anna pelilaudan koko: ");
      chessBoardSize = Integer.parseInt(input.nextLine());
      System.out.print("Anna tulostuksien määrä: ");
      printValue = Integer.parseInt(input.nextLine());
      if(printValue>chessBoardSize)printValue=chessBoardSize;
      input.close();
    } catch (Exception e) {
      System.out.println("Virhe: "+e);
      return;
    }
    // -------------------------------------------------------------

    QSolver s = new QSolver(chessBoardSize);
    System.out.println("Ohjelma alkoi ja etsitään " +ANSI_YELLOW +chessBoardSize +ANSI_RESET+ " kuningattarelle paikat.");
    long start = System.currentTimeMillis();
    ArrayList<int[][]> result = new ArrayList<int[][]>(s.startGame());
    long elapsedTimeMillis = System.currentTimeMillis() - start;
    float elapsedTimeSec = elapsedTimeMillis / 1000F;
    System.out.println("Ratkaisuja löytyi yhteensä: " +ANSI_YELLOW +s.getsolutionsCount() +ANSI_RESET +" kpl.");
    System.out.println("Aikaa meni: " + ANSI_YELLOW+(elapsedTimeSec) +ANSI_RESET+ " Sekuntia");
    s.printer(result,chessBoardSize, printValue);
  }
}

class QSolver {
  public static final String ANSI_RESET = "\033[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";
  private int size;
  private int solutionsCount;
  // konstruktori6
  public QSolver(int boardSize) {
    size = boardSize;
  }

  public void printer(ArrayList<int[][]> arr, int boardWidth , int howManyToPrint) {
    int counter=howManyToPrint;
    for (int[][] a : arr) {
      if(counter<=0)break;
      System.out.println("Tulostus numero: "+(howManyToPrint-counter+1));
      for (int i = 0; i < a.length; i++) { //this equals to the row in our matrix.
        for (int j = 0; j < a[i].length; j++) {
          if(a[i][j]==8){
            //System.out.print(ANSI_GREEN+"X");         <---- Värit eivät toimi kaikissa terminaaleissa
            System.out.print((j+1)+" ");
          } 
          else{
            System.out.print("* ");
            //System.out.print(ANSI_RESET+"* ");         <---- Värit eivät toimi kaikissa terminaaleissa
          }
        }
        System.out.println();
     }
     System.out.println("-----------------------");
     counter-=1;
    }
  }
  public int getsolutionsCount(){
    return this.solutionsCount;
  }
  public ArrayList<int[][]> startGame() {
    /**
     * Varsinainen ohjelma suoritetaan täällä(tällä hetkellä kovinkin sekalainen)
     * tarkoitus on siistiä sitä jakamalla toimintoja eri metodeille
     */
    ArrayList<Integer> tempPath = new ArrayList<Integer>();
    int[][] board = new int[this.size][this.size];
    ArrayList<int[][]> allPaths = new ArrayList<int[][]>(); // tarkempi lista pelilaudasta ja kuningattarien
                                                            // sijoituksista
    ArrayList<ArrayList<Integer>> allPathsInList = new ArrayList<ArrayList<Integer>>();
    int col = 0;
    int startRow = 0;
    int addedQuuens = 0;
    int flawColumn = 0;
    
    while (true) {
      if (col == 0 && startRow > this.size - 1)
        break; // mikäli kaikki vaihtoehdot ovat käyty läpi pysähtyy ohjelma tähän
      flawColumn = 0;
      
      for (int row = startRow; row < this.size; row++) {
        
        if (isPossible(col, row, board,tempPath)) {
          addedQuuens += 1;
          board[col][row] = 8;
          tempPath.add(row);
          col += 1;
          startRow = 0;
          break;
        }else{
          flawColumn += 1;
          board[col][row] = 0;
        }
      }
      
      if (flawColumn >= this.size || (flawColumn + startRow) >= this.size || col >= this.size) {
        if (addedQuuens == this.size) {
          allPaths.add(Arrays.stream(board).map(a -> Arrays.copyOf(a, a.length)).toArray(int[][]::new)); //kopioidaan oikea ratkaisu array allPath listaan
          allPathsInList.add(new ArrayList<Integer>(tempPath));
        }
        addedQuuens -= 1;
        startRow = tempPath.get(tempPath.size() - 1)+1;
        col = col - 1;
        board=clearBoard(board,col);
        tempPath.remove(tempPath.size() - 1);
      }
    }
    this.solutionsCount=allPathsInList.size();
    return allPaths;
  }

  private boolean isPossible(int posCol, int posRow, int[][] queensOnBoard, ArrayList<Integer> checkAllQueenPositions) {
    /**
     * Metodi tarkistaa voiko kyseiseen ruutuun sijoittaa kuningattaren mikäli voi
     * palauttaa true arvon, muuten palauttaa falsen.
     */
    // Tarkistetaan kaikki rivit taaksepäin onko rivi vapaa
    if(checkAllQueenPositions.contains(posRow)){
      return false;
    }
    int tempRow = posRow;
    
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

  private int[][] clearBoard(int[][] boardToClean,int toColumn){
    /**
     * Alustetaan pelilauta nollilla toColumniin asti jotta voidaan jatkaa puhtaalta pöydältä
     */
    for (int i = toColumn; i < boardToClean.length; i++) {
      for (int j = 0; j < boardToClean.length; j++) {
        boardToClean[i][j] = 0;
      }
    }
    return boardToClean;
  }

}