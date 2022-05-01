package Chessboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/** 
 * 
 * Backtracking menetelmä etsimään kaikki vaihtoehdot asettaa
 * kuningattaret pelilaudalle uhkaamatta toisiaan: Pelilaudan kokoa voi
 * muuttaa(minimi 4), maksimi ei ole mutta luonnollisesti mitä isompi pelilauta
 * == tarvitaan tehoja koneesta. oletuksena on kahdeksan jolloin pitäisi löytyä
 * 92 eri vaihtoehtoa
 * 
 * https://www.datagenetics.com/blog/august42012/ Sivulta voi tarkistaa toimiiko
 * algoritmi sekä löytyy kaikki eri vaihtoehdot pelilaudan ratkaisuiden määrä
 * pelilaudan koko - ratkaisuja 
 * 1 x 1        ---     1 
 * 2 x 2        ---     0 
 * 3 x 3        ---     0 
 * 4 x 4        ---     2 
 * 5 x 5        ---     10 
 * 6 x 6        ---     4 
 * 7 x 7        ---     40 
 * 8 x 8        ---     92 
 * 9 x 9        ---     352 
 * 10 x 10      ---     724 
 * 11 x 11      ---     2,680 
 * 12 x 12      ---     14,200 
 * 13 x 13      ---     73,712 
 * 14 x 14      ---     365,596
 */
public class EightQueens {
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    // Pelikentän koon alustaminen sekä eri tulostuksien määrän asettaminen tapahtuu ohjelman suorittamalla konsolissa asetettavilla määrillä.

    // ------------------------------------------------------------- ALUSTUS KONSOLISSA!!!
    int chessBoardSize=4;
    int printValue=1;
    try {
      System.out.print("Anna pelilaudan koko(kokonaislukuna): ");
      chessBoardSize = Integer.parseInt(input.nextLine());
      System.out.print("Anna tulostuksien maara(kokonaislukuna): ");
      printValue = Integer.parseInt(input.nextLine());
      if(printValue>chessBoardSize)printValue=chessBoardSize;
      input.close();
    } catch (Exception e) {
      System.out.println("Virhe, vaara syote: "+e);
      System.out.println("Ohjelma lopetataan!");
      return;
    }
    // ------------------------------------------------------------- ALUSTUS KONSOLISSA!!!

    // ilmoitetaan käyttäjälle infot: mitä tehdään -> kauanko aikaa menee -> mitä löytyi -> sekä tulostetaan ne konsoliin
    QSolver s = new QSolver(chessBoardSize);
    System.out.println("Ohjelma alkoi ja etsitaan " +chessBoardSize + " kuningattarelle paikat.");
    long start = System.currentTimeMillis();
    ArrayList<int[][]> result = new ArrayList<int[][]>(s.startGame());
    long elapsedTimeMillis = System.currentTimeMillis() - start;
    float elapsedTimeSec = elapsedTimeMillis / 1000F;
    System.out.println("Ratkaisuja loytyi yhteensa: " +result.size() + " kpl.");
    System.out.println("Aikaa meni: " + (elapsedTimeSec) + " Sekuntia");
    s.printer(result,chessBoardSize, printValue);
  }
}

class QSolver {
  
  private int size;

  // konstruktori
  public QSolver(int boardSize) {
    size = boardSize;
  }

  public void printer(ArrayList<int[][]> arr, int boardWidth , int howManyToPrint) {
    /**
     * Tulostaa kaikki listan vaihtoehdot oikean kokoisena(boardWidth) matrsiisina.
     * Tulostuksia tulostetaan haluttu määrä(howManyToPrint) asti.
     * kuningattaret on numeroitu asetus järjestyksessä tarkastuksen helpottamiseksi
     */
    int counter=howManyToPrint;
    for (int[][] a : arr) {
      if(counter<=0)break;
      System.out.println("Tulostus numero: "+(howManyToPrint-counter+1));
      for (int i = 0; i < a.length; i++) {
        for (int j = 0; j < a[i].length; j++) {
          if(a[i][j]==8){
            System.out.print((j+1)+" ");
          } 
          else{
            System.out.print("* ");
          }
        }
        System.out.println();
     }
     System.out.println("-----------------------");
     counter-=1;
    }
  }
  
  public ArrayList<int[][]> startGame() {
    /**
     * Varsinainen ohjelma suoritetaan täällä(tällä hetkellä kovinkin sekalainen)
     * tarkoitus on siistiä sitä jakamalla toimintoja eri metodeille
     */
    ArrayList<Integer> tempPath = new ArrayList<Integer>(); //<- väliaikaista listaa käytetään kuningattarien sijantien tarkistukseen sekä uusien sen hetkisten sijaintien asettamiseen
    int[][] board = new int[this.size][this.size];
    ArrayList<int[][]> allPaths = new ArrayList<int[][]>(); // tarkempi lista pelilaudasta ja kuningattarien sijoituksista
    int col = 0; //<-osoittaa sen hetkisen sijainti sarakkeen
    int startRow = 0; //<- osoittaa sen hetkisen aloitus rivin
    int addedQuuens = 0; //<- kertoo sen hetkisen kuningattarien määrän pelilaudalla(board)
    int flawColumn = 0; //<- kertoo rivien määrän jotka tarkistettu, mutta ei pystytty asettamaan kuningatarta
    
    while (true) {
      if (col == 0 && startRow > this.size - 1)break;// mikäli kaikki vaihtoehdot ovat käyty läpi pysähtyy ohjelma tähän 
      flawColumn = 0;
      // Loopataan startRow(vaihtelee riippuen backtracking tilanteesta) sijainnista aina pelilaudan kokoon asti kaikki mahdolliset vaihtoehdot
      // ja asetetaan kuningatar pelilaudalle sekä päivitetään muuttujat(sijainnit ja kuningattarien määrä), mikäli tämä ei onnistu
      // asetetaan pelilaudalle(board) 0 ja lisätään hylätyn rivin määrää.
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
          board[col][row] = 0;// tyhjä asetetut rivit voi laittaa haluamalla int numerolla debuggauksen helpottamiseksi.
        }
      }

      // Mikäli ollaan pelilaudan jollain reunalla tarkistetaan onko kaikki kuningattaret asetettu ja jos on, lisätään ne listaan.
      // tämän jälkeen "peruutetaan" eli alustetaan muuttujat siten että jatketaan saman kolumnin seuraavalta riviltä ja etsitään 
      // mahdollista uutta sijoituspaikkaa kuningattarelle
      if ((flawColumn + startRow) >= this.size || col >= this.size) {
        if (addedQuuens == this.size) {
          allPaths.add(Arrays.stream(board).map(a -> Arrays.copyOf(a, a.length)).toArray(int[][]::new)); //kopioidaan oikea ratkaisu array allPath listaan
        }
        addedQuuens -= 1;
        startRow = tempPath.get(tempPath.size() - 1)+1;
        col = col - 1;
        board=clearBoard(board,col); // metodi joka tyhjentää pelilaudan backtracking vaiheessa haluttuun kolumniin asti
        tempPath.remove(tempPath.size() - 1);
      }
    }
    return allPaths;
  }

  private boolean isPossible(int posCol, int posRow, int[][] queensOnBoard, ArrayList<Integer> checkAllQueenPositions) {
    /**
     * Metodi tarkistaa voiko kyseiseen ruutuun sijoittaa kuningattaren mikäli voi
     * palauttaa true arvon, muuten palauttaa falsen.
     */

    //alustetaan väliaikainen muuttuja jota käytetään forloopeissa.
    int tempRow = posRow;

    // Tarkistetaan kaikki rivit taaksepäin onko rivi vapaa
    if(checkAllQueenPositions.contains(posRow)){
      return false;
    }
    
    // tarkistetaan vasen yläviisto kunnes pelilaudan jokin reuna tulee vastaan, jolloin lopetetaan tarkastus
    // mikäli vastaan tulee kuningatar(8) ilmoitetaan sijainti varatuksi
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
    // tarkistetaan vasen alaviisto kunnes pelilaudan jokin reuna tulee vastaan, jolloin lopetetaan tarkastus
    // mikäli vastaan tulee kuningatar(8) ilmoitetaan sijainti varatuksi
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
     * Alustetaan pelilauta nollilla mikäli haluttuun kohtaan(toColumn) ollaan menossa taaksepäin backtracking askelilla
     */
    for (int i = toColumn; i < boardToClean.length; i++) {
      for (int j = 0; j < boardToClean.length; j++) {
        boardToClean[i][j] = 0;
      }
    }
    return boardToClean;
  }

}