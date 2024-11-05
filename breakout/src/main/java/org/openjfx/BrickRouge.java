package org.openjfx;

import javafx.scene.paint.Color;

public class BrickRouge extends Brick{


    private final int score;
    private PowerUp powerup;

    public BrickRouge(boolean isNotBreak, double brickWidth, double brickHeight){
        super(isNotBreak, brickWidth, brickHeight);
        this.score = 50;
        
             
    }
    

    public Color couleur(){
        return Color.RED;
    }
    
    public int getScore(){
        return this.score;
    }

    public void setPowerUp(PowerUp p){
        this.powerup = p;
    }

    public PowerUp getPowerUp(){
        return this.powerup;
    }



    


    
}
