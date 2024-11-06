package org.openjfx;

import javafx.scene.paint.Color;

public class BrickDouble extends Brick {


    private int nbrVie;
    private final int score;
    public BrickDouble(boolean isNotBreak, double brickWidth, double brickHeight, PowerUp powerup){
        super(isNotBreak, brickWidth, brickHeight,powerup);
        this.score = 30;
        this.nbrVie = 1;
        setHavePower(true);

    
    }

     public Color couleur(){
        return Color.GREEN;
    }

    public int getScore(){
        return this.score;
    }
    
    public int getNbrVie(){
        return this.nbrVie;
    }


    public void setNbrVie(int nbrVie) {
        this.nbrVie = nbrVie;
    }


}
