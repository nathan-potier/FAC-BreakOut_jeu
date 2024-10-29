package org.openjfx;
import java.util.Random;


public class Niveau {

    private int brickRows;
    private int brickCols = 12;
    private int diff; /* diff >=1 */


    // crée un niveau à partir d'une difficulté 
    Niveau(int diff){
        this.diff = (diff < 1)? 1 : Math.min(diff, 10);
        brickRows = Math.min(5 + this.diff - 1, 14);
    }


    // constructeur pour niveau personnalisé
    Niveau(){
        diff = 0;
    }

    public int getDiff(){
        return diff;
    }


    public int getBrickRows(){
        return brickRows;
    }

    public void setBrickRows(int n){
        brickRows = n;
    }
    
    //génère aléatoirement en fonction de la difficulté 
    //une configuration de brique différente sous forme de tableau de brique
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
        System.out.print(tab.length + "" + tab[0].length);
        return tab;
    }
}
