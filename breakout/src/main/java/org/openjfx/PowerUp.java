package org.openjfx;

import java.util.Random;

public class PowerUp {

    private static int PUsize = 10; 
    private double PUx = BreakOut.getWidth() / 2;;
    private double PUy = BreakOut.getHeight() / 2;;
    private boolean tombe = false;
    private boolean actif;
    private static double PUyspeed = 1;

    public PowerUp(double PUx, double PUy){
        this.PUx = PUx;
        this.PUy = PUy;
    }
    
    public boolean getTombe(){
        return this.tombe;
    }

    public void setTombe(boolean tombe){
        this.tombe = tombe;
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

    public static PowerUp randomPU(double x ,double y){
        Random r = new Random();
        double z = r.nextInt(3);
        if(z == 0){
            return new MoreBall(x,y);
        }else if( z == 1){
            return new MoreRacketSize(x,y);
        }else{
            return new MoreBall(x,y);
        }
    }

    
}
