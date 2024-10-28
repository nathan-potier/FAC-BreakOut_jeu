package org.openjfx;

public class TreeBall extends PowerUp{

    private static Balle[] tab;
    private boolean actif;

    

    public TreeBall(double PUx, double PUy, double ballXPos, double ballYPos) {
        super(PUx, PUy);
        tab = new Balle[3];
        tab[0] = new Balle(ballXPos,ballYPos);
        tab[1] = new Balle(ballXPos +10,ballYPos);
        tab[2] = new Balle(ballXPos +20,ballYPos);
    }


    public void applyEffect(){
        this.actif = true;
    }

    public boolean getActif(){
        return this.actif;
    }

    public void setActif(boolean actif){
        this.actif = actif;
    }

    
    
        


    
}
