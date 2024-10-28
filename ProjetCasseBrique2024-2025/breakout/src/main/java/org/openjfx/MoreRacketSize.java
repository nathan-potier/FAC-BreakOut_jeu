package org.openjfx;

public class MoreRacketSize extends PowerUp {

    public MoreRacketSize(double Pux, double PUy) {
        super(Pux, PUy);
    }


    @Override
    public void applyEffect(){
        Racket.setRacketWidth(Racket.getWidth()*2);
    }


    


    
}
