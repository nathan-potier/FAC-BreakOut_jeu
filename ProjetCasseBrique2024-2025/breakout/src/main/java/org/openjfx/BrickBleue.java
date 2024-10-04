package org.openjfx;

import javafx.scene.paint.Color;

public class BrickBleue extends Brick{


    public BrickBleue(boolean isNotBreak, double brickWidth, double brickHeight){
        super(isNotBreak, brickWidth, brickHeight);
        

    }
    

    public Color couleur(){
        return Color.BLUE;
    }

    

    
}
