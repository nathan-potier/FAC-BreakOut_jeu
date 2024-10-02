package org.openjfx;

import javafx.scene.paint.Color;

public class BrickUnbreakable extends Brick{

    public boolean isNotBreak;

    public BrickUnbreakable(boolean isNotBreak){
        super(isNotBreak);

    }

    public Color couleur(){
        return Color.GRAY;
    }
    
}
