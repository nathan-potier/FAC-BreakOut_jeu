package org.openjfx;

public class TreeBall extends PowerUp{

    private Balle balle1;
    private Balle balle2;
    private Balle balle3;

    

    public TreeBall(double PUx, double PUy) {
        super(PUx, PUy);
    }


    public void applyEffect(){
        this.balle1 = new Balle(BreakOut.getWidth() / 2,BreakOut.getHeight() / 2);
        this.balle2 = new Balle(BreakOut.getWidth() / 2 +10,BreakOut.getHeight() / 2);
        this.balle3 = new Balle(BreakOut.getWidth() / 2 +20,BreakOut.getHeight() / 2);
        balle1.rebondHorizontal();
        balle2.rebondVertical();
        BreakOut.balles.add(balle1);
        BreakOut.balles.add(balle2);
        BreakOut.balles.add(balle3);
        BreakOut.nbBalle = BreakOut.nbBalle + 3;
    }

    
}
