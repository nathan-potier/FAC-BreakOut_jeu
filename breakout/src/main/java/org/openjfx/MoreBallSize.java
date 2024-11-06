package org.openjfx;

public class MoreBallSize extends PowerUp{

    

    public MoreBallSize(double PUx, double PUy) {
        super(PUx,PUy);
        

    }

    public void applyEffect(){
        Balle.setBallSize(50);
    }

    
}
