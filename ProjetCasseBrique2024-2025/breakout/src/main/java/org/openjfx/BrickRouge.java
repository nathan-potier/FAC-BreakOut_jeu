package org.openjfx;

import javafx.scene.paint.Color;

public class BrickRouge extends Brick{



    public BrickRouge(boolean isNotBreak, double brickWidth, double brickHeight){
        super(isNotBreak, brickWidth, brickHeight);
        

    }
    

    public Color couleur(){
        return Color.RED;
    }

    


    
}
