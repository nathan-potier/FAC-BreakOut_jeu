package org.openjfx;

import javafx.scene.paint.Color;

public class Brick {

    
    public boolean isNotBreak;
    private final double brickWidth;
    private final double brickHeight;

    public Brick(boolean isNotBreak, double brickWidth, double brickHeight){
        this.isNotBreak = isNotBreak;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;

        

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
}
