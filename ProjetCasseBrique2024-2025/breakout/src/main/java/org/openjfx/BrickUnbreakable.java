package org.openjfx;

import javafx.scene.paint.Color;

public class BrickUnbreakable extends Brick{


    public BrickUnbreakable(boolean isNotBreak, double brickWidth, double brickHeight){
        super(isNotBreak, brickWidth, brickHeight);
        

    }

    public Color couleur(){
        return Color.GRAY;
    }

    
    
}
