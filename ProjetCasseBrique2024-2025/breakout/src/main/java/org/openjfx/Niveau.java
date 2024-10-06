package main.java.org.openjfx;
import java.util.Random;


public class Niveau {

    private int nbRows;
    private int nbCols;
    private int brickRows = 5;
    private int brickCols = 12;
    private int diff; /* diff >=1 */

    Niveau(int row, int col, int bCols, int bRows, int diff){
        nbCols = col;
        nbRows = row;
        brickCols = bCols;
        brickRows = bRows;
        this.diff = diff;
    }


    
    private Brick[][] makeNiveau(){
        Random rd = new Random();
        Brick[][] tab = new Brick[nbRows][nbCols];
        /* nb de ligne de brique incrémenté de 1 par niveau de difficulté -1 */
        for(int i = 0; i < brickRows + diff -1; i++){
            for (int j = 0; j < brickCols; j++) {
                int r = rd.nextInt(100);

                /* probabilité d'avoir une brique incassable (augmente selon la diff */
                if (r < diff*2){
                    tab[i][j] = new BrickUnbreakable(true, 80, 20);
                /* probabilité d'avoir une brique avec bonus  (diminue selon la diff*/
                }else if (r < 25){
                    tab[i][j] = new BrickRouge(true, 80, 20);
                }else{
                    tab[i][j] = new BrickBleue(true, 80, 20);
                }

            }
        }
        int r = rd.nextInt(100);


        return null;
    }
}
