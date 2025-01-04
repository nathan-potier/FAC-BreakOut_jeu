package org.openjfx;

public class TpBalle extends PowerUp{

    public TpBalle(double PUx, double PUy, boolean malus) {
        super(PUx,PUy,malus);
    
    }

    @Override
    public void applyEffect(){
        GameState.wallOpen = true;

    }
}
