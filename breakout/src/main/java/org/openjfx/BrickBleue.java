package org.openjfx;

import javafx.scene.paint.Color;

public class BrickBleue extends Brick{

    private final int score;

    public BrickBleue(boolean isNotBreak, double brickWidth, double brickHeight, PowerUp powerup){
        super(isNotBreak, brickWidth, brickHeight, powerup);
        this.score = 10;
        setHavePower(false);
        

    }
    

    public Color couleur(){
        return Color.BLUE;
    }

    public int getScore(){
        return this.score;
    }

    

    
}
