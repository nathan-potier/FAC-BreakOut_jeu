package org.openjfx;


public class Balle {
    private int ballYSpeed = 3;
	private int ballXSpeed = 3;
    private static int ballSize = 10;   
    private double ballXPos = BreakOut.getWidth() / 2; // ball at the center at game start
    private double ballYPos = BreakOut.getHeight() / 2;


    public int getYSpeed(){ return this.ballYSpeed; }
    public int getXSpeed(){ return this.ballXSpeed; }

    public static int getSize(){ return ballSize; }

    public double getXPos(){ return this.ballXPos; }
    public double getYPos(){ return this.ballYPos; }

    public void reset(){
        ballXSpeed = 3;
        ballYSpeed = 3;
        ballYPos   = BreakOut.getHeight() / 2;
        ballXPos   = BreakOut.getWidth() / 2;
    }

    public void rebondVertical(){
        this.ballYSpeed = -this.ballYSpeed; // Rebond vertical
    }

    public void rebondHorizontal(){
        this.ballXSpeed = -this.ballXSpeed; // Rebond horizontal

    }

    public void Rebond(){
        this.ballYSpeed = -this.ballYSpeed; // Rebond de la balle
    }

    public void MAJPosHorizontal(){
        this.ballXPos += this.ballXSpeed;
    }

    public void MAJPosVertical(){
        this.ballYPos += this.ballYSpeed;
    }

    public static int getBallSize(){
        return ballSize;
    }

    public static void setBallSize(int ballsize){
        ballSize = ballsize;
    }

    public static void resetSize(){
        ballSize = 10;
    }

}
