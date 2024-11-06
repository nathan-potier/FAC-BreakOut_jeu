package org.openjfx;

import javafx.scene.paint.Color;

public class BrickRouge extends Brick{


    private final int score;

    public BrickRouge(boolean isNotBreak, double brickWidth, double brickHeight,PowerUp powerup){
        super(isNotBreak, brickWidth, brickHeight, powerup);
        this.score = 50;
        setHavePower(true);

    }
    

    public Color couleur(){
        return Color.RED;
    }
    
    public int getScore(){
        return this.score;
    }


    public void powerUp(){
        
    }

    


    
}
