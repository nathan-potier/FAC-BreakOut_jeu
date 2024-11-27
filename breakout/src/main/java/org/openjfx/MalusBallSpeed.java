package org.openjfx;

public class MalusBallSpeed extends PowerUp{

    public MalusBallSpeed(double PUx, double PUy, boolean malus) {
        super(PUx, PUy, malus);
    }

    @Override
    public void applyEffect(){
        for(int i = 0; BreakOut.balles.size() > i; i++){
            BreakOut.balles.get(i).setXSpeed(BreakOut.balles.get(i).getXSpeed()*2);
            BreakOut.balles.get(i).setYSpeed(BreakOut.balles.get(i).getYSpeed()*2);
        }

    }
    
}
