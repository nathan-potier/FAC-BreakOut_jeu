package org.openjfx;

public class Balle {
    private static int ballYSpeed = 3;
	private static int ballXSpeed = 3;
    private static int ballSize = 10;   
    private static double ballXPos = BreakOut.getWidth() / 2; // ball at the center at game start
    private static double ballYPos = BreakOut.getHeight() / 2;


    public static int getYSpeed(){ return ballYSpeed; }
    public static int getXSpeed(){ return ballXSpeed; }

    public static int getSize(){ return ballSize; }

    public static double getXPos(){ return ballXPos; }
    public static double getYPos(){ return ballYPos; }

    public static void reset(){
        ballXSpeed = 3;
        ballYSpeed = 3;
        ballYPos   = BreakOut.getHeight() / 2;
        ballXPos   = BreakOut.getWidth() / 2;
    }

    public static void rebondVertical(){
        ballYSpeed = -ballYSpeed; // Rebond vertical
    }

    public static void rebondHorizontal(){
        ballXSpeed = -ballXSpeed; // Rebond horizontal

    }

    public static void Rebond(){
        ballYSpeed = -ballYSpeed; // Rebond de la balle
    }

    public static void MAJPosHorizontal(){
        ballXPos += ballXSpeed;
    }

    public static void MAJPosVertical(){
        ballYPos += ballYSpeed;
    }

}
