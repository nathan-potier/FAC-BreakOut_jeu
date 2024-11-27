package org.openjfx;

public class MalusRacket extends PowerUp{

    public MalusRacket(double PUx, double PUy, boolean malus) {
        super(PUx, PUy, malus);
    }

    @Override
    public void applyEffect(){
        Racket.setRacketWidth(Racket.getWidth()/2);
    }
    
}
