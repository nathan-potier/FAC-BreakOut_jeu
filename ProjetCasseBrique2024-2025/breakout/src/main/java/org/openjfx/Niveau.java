package org.openjfx;
import java.util.Random;


public class Niveau {

    private final int brickRows;
    private final int brickCols;
    private final int diff; /* diff >=1 */

    Niveau(int bRows, int bCols, int diff){
        brickRows = bRows + diff - 1;
        brickCols = bCols;
        this.diff = diff;
    }


    public int getBrickRows(){
        return brickRows;
    }
    
    public Brick[][] makeNiveau(){
        Random rd = new Random();
        Brick[][] tab = new Brick[brickRows][brickCols];
        
        // nb de ligne de brique incrémenté de 1 par niveau de difficulté -1 
        for(int i = 0; i < brickRows; i++){
            for (int j = 0; j < brickCols; j++) {
                int val = rd.nextInt(100);

                // probabilité d'avoir une brique incassable (augmente selon la diff)
                if (val < diff*2){
                    tab[i][j] = new BrickUnbreakable(true, 80, 20);
                
                    // probabilité d'avoir une brique avec bonus  (diminue selon la diff)
                }else if (val < 20){
                    tab[i][j] = new BrickRouge(true, 80, 20); 

                    //probabilité d'avoir une brique a 2 pv (augmente selon la diff)
                }else if (val < 20 + (5 * diff)) {
                    tab[i][j] = new BrickDouble(true, 80, 20);

                }else{
                    tab[i][j] = new BrickBleue(true, 80, 20);
                }

            }
        }
        return tab;
    }
}
