package org.openjfx;

public class PowerUp {

    private static int PUsize = 10; 
    private double PUx = BreakOut.getWidth() / 2;;
    private double PUy = BreakOut.getHeight() / 2;;
    private boolean actif = false;
    private static double PUyspeed = 1;

    public PowerUp(double PUx, double PUy){
        this.PUx = PUx;
        this.PUy = PUy;
    }
    
    public boolean getActif(){
        return this.actif;
    }

    public void setActif(boolean actif){
        this.actif = actif;
    }

    public  double getPux(){
        return this.PUx;
    }

    public  double getPuy(){
        return this.PUy;
    }

    public void actuPUy(){
        this.PUy += PUyspeed;
    }

    public static double getSize(){
        return PUsize;
    }

    public void applyEffect(){

    }
}
