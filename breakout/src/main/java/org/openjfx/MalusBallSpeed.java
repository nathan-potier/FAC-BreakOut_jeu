package org.openjfx;

public class MalusBallSpeed extends PowerUp{

    public MalusBallSpeed(double PUx, double PUy, boolean malus) {
        super(PUx, PUy, malus);
    }

    @Override
    public void applyEffect(){
        for(int i = 0; NiveauActuel.balles.size() > i; i++){
            NiveauActuel.balles.get(i).setXSpeed(NiveauActuel.balles.get(i).getXSpeed()*2);
            NiveauActuel.balles.get(i).setYSpeed(NiveauActuel.balles.get(i).getYSpeed()*2);
        }

    }
    
}
