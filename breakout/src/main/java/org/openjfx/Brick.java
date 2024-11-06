package org.openjfx;

import javafx.scene.paint.Color;

public class Brick {

    private int nbrVie;
    public boolean isNotBreak;
    private final double brickWidth;
    private final double brickHeight;
    private final int score; 
    private PowerUp powerup;
    private boolean havePower;

    public Brick(boolean isNotBreak, double brickWidth, double brickHeight, PowerUp powerup){
        this.isNotBreak = isNotBreak;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.score = 0;
        this.nbrVie = 0;
        this.powerup = null;
        this.havePower = false;
        this.powerup = powerup;

        

    }
    

    public Color couleur(){
        return null;
    }

    public double getWidth(){
        return this.brickWidth;
    }

    public double getHeight(){
        return this.brickHeight;
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

    public void setPowerUp(PowerUp p){
        this.powerup = null;
    }

    public void setHavePower(boolean havePower){
        this.havePower = havePower;
    }

    public boolean getHavePower(){
        return this.havePower;
    }

    public PowerUp getPowerUp(){
        return this.powerup;
    }
}
