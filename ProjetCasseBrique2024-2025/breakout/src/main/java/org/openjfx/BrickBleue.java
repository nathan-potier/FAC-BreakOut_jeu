package org.openjfx;

import javafx.scene.paint.Color;

public class BrickBleue extends Brick{


    public BrickBleue(boolean isNotBreak){
        super(isNotBreak);

    }
    

    public Color couleur(){
        return Color.BLUE;
    }

    
}
