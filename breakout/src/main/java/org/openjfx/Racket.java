package org.openjfx;

public class Racket {
    private static int racketWidth = 50;
    private static int racketHeight = 10;
    private static double racketXPos = BreakOut.getWidth() / 2;
    private static double racketYPos = BreakOut.getHeight() - 40;

    public static void setXpos(Double Xpos){ racketXPos = Xpos; }


    public static int getHeight() { return racketHeight ;}
    public static int getWidth()  { return racketWidth ;}

    public static double getXPos(){ return racketXPos ;}
    public static double getYPos(){ return racketYPos ;}

    public static void setRacketWidth(int width){
        racketWidth = width;
    }

    public static void resetSize(){
        racketWidth = 50;
    }
}
