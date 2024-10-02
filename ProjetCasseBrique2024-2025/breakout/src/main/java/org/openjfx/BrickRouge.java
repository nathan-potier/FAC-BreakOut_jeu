package org.openjfx;

import javafx.scene.paint.Color;

public class BrickRouge extends Brick{



    public BrickRouge(boolean isNotBreak){
        super(isNotBreak);

    }
    

    public Color couleur(){
        return Color.RED;
    }


    
}
