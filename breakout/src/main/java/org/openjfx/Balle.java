package org.openjfx;

/**
 * La classe Balle sert à générer des Balles utilisables dans un jeu de Breakout.
 * 
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul>
 * <li> Renvoie la vitesse Y de la Balle {@link #getYSpeed()}.</li>
 * <li> Renvoie la vitesse X de la Balle {@link #getXSpeed()}.</li>
 * <li> Retourne la taille des Balles {@link #getSize()}.</li>
 * <li> Retourne la position X de la balle {@link #getXPos()}.</li>
 * <li> Renvoie la position Y de la balle {@link #getYPos()}.</li>
 * <li> Reinitialise a ses valeurs initiales les parametres de la balle {@link #reset()}.</li>
 * <li> Fait rebondir verticalement la balle {@link #rebondVertical()}.</li>
 * <li> Fait rebondir horizontalement la balle {@link #rebondHorizontal()}.</li>
 * <li> ?? {@link #Rebond()}.</li>
 * <li> Met a jour la position X de la balle {@link #MAJPosHorizontal()}.</li>
 * <li> Met a jour la postion Y de la balle {@link #MAJPosVertical()}.</li>
 * <li> ??  {@link #getBallSize()}.</li>
 * <li> Renvoie un PowerUp Aléatoire entre les PowerUp disponibles {@link #setBallSize(int)}.</li>
 * <li> Renvoie un PowerUp Aléatoire entre les PowerUp disponibles {@link #resetSize()}.</li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */

public class Balle {
    private int ballYSpeed = 3;
	private int ballXSpeed = 3;
    private static int ballSize = 10;   
    private double ballXPos;
    private double ballYPos;

    public Balle(double ballXPos, double ballYPos){
        this.ballXPos = ballXPos;
        this.ballYPos = ballYPos;
    }


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
