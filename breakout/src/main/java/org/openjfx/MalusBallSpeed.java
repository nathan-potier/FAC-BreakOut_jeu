package org.openjfx;

public class MalusBallSpeed extends PowerUp{

    public MalusBallSpeed(double PUx, double PUy, boolean malus) {
        super(PUx, PUy, malus);
    }

    @Override
    public void applyEffect(){
        Balle.setYSpeed(Balle.getYSpeed()*2);
        Balle.setXSpeed(Balle.getXSpeed()*2);
    }
    
}
