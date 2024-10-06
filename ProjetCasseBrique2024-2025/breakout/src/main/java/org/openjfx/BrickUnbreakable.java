package org.openjfx;

import javafx.scene.paint.Color;

public class BrickUnbreakable extends Brick{

    private final int score;
    public BrickUnbreakable(boolean isNotBreak, double brickWidth, double brickHeight){
        super(isNotBreak, brickWidth, brickHeight);
        this.score = 10;
        

    }

    public Color couleur(){
        return Color.GRAY;
    }

    public int getScore(){
        return this.score;
    }

    
    
}
