package org.openjfx;

import java.util.Random;

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
 * <li> Met a jour la position X de la balle {@link #MAJPosHorizontal()}.</li>
 * <li> Met a jour la postion Y de la balle {@link #MAJPosVertical()}.</li>
 * <li> Met la taille des balles au int donner {@link #setBallSize(int)}.</li>
 * <li> Réinitialise la taille des balles {@link #resetSize()}.</li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */

public class Balle {
    private int ballYSpeed ;
	private int ballXSpeed ;
    private static int ballSize = 10;   
    private double ballXPos;
    private double ballYPos;
    private final int speedNorm = 3;

    /**
     * Constructeur d'une Balle définie par plusieurs paramètres.
     *  
     * @param ballXPos un double représentant la position X de la balle
     * @param BallYPos un double représentant la position Y de la balle
    */

    public Balle(double ballXPos, double ballYPos) {
        this.ballXPos = ballXPos;
        this.ballYPos = ballYPos;
        initRandomSpeed();
    }

    /**
     * Initialise une direction aléatoire pour la balle.
     */
    public void initRandomSpeed() {
        Random rand = new Random();
        double angle = rand.nextDouble() * 2 * Math.PI; // Angle aléatoire en radians
        this.ballXSpeed = (int) Math.round(speedNorm * Math.cos(angle));
        this.ballYSpeed = (int) Math.round(speedNorm * Math.sin(angle));

        // Évite que l'une des vitesses soit 0 pour une meilleure dynamique
        if (ballXSpeed == 0 || ballYSpeed == 0) {
            initRandomSpeed();
        }
    }

    /**
     * Retourne la vitesse en Y de la balle.
     * 
     * @return la vitesse en Y de la balle.
     */
    public int getYSpeed(){ return this.ballYSpeed; }
    /**
     * Retourne la vitesse en X de la balle.
     * 
     * @return la vitesse en X de la balle.
     */
    public int getXSpeed(){ return this.ballXSpeed; }

    /**
     * Retourne la taille des balles.
     * 
     * @return la taille des balles.
     */
    public static int getSize(){ return ballSize; }

    /**
     * Retourne la position en X de la balle.
     * 
     * @return la position en X de la balle.
     */
    public double getXPos(){ return this.ballXPos; }

    /**
     * Retourne la position en Y de la balle.
     * 
     * @return la position en Y de la balle.
     */
    public double getYPos(){ return this.ballYPos; }

    /**
     * 
     *réinitialise les paramètres de base de la balle.
     */
    public void reset(){
        initRandomSpeed();
        ballYPos   = BreakOut.getHeight() / 2;
        ballXPos   = BreakOut.getWidth() / 2;
    }

    /**
     * 
     *fait rebondir la balle verticalement.
     */
    public void rebondVertical(){
        this.ballYSpeed = -this.ballYSpeed; // Rebond vertical
    }
    
    public void rebondSurRaquette(double raquetteX, int raquetteLargeur) {
        // Calcul de la position relative de l'impact (entre -1 et 1)
        double positionRelative = (this.ballXPos - (raquetteX + raquetteLargeur / 2)) / (raquetteLargeur / 2);
    
        // Limite la valeur pour éviter des angles extrêmes
        positionRelative = Math.max(-1, Math.min(1, positionRelative));
    
        // Calcul du nouvel angle en fonction de l'impact
        double angle = Math.PI / 4 * positionRelative; // Angle entre -45° et +45°
    
        // Met à jour les vitesses X et Y en respectant la norme
        double speed = Math.sqrt(ballXSpeed * ballXSpeed + ballYSpeed * ballYSpeed);
        int newBallXSpeed = (int) Math.round(speed * Math.sin(angle));
        int newBallYSpeed = -(int) Math.round(speed * Math.cos(angle)); // Direction vers le haut
    
        // Assurer que la direction horizontale respecte la direction actuelle
        if (Math.signum(ballXSpeed) != Math.signum(newBallXSpeed)) {
            newBallXSpeed = -newBallXSpeed;
        }
    
        // Mise à jour des vitesses
        this.ballXSpeed = newBallXSpeed;
        this.ballYSpeed = newBallYSpeed;
    }
    

    /**
     * 
     *fait rebondir la balle horizontalement.
     */
    public void rebondHorizontal(){
        this.ballXSpeed = -this.ballXSpeed; // Rebond horizontal

    }


    /**
     * 
     *actualise la position X de la balle.
     */
    public void MAJPosHorizontal(){
        this.ballXPos += this.ballXSpeed;
    }

    /**
     * 
     *actualise la position Y de la balle.
     */
    public void MAJPosVertical(){
        this.ballYPos += this.ballYSpeed;
    }

    /**
     * 
     *@param ballsize met la taille des balles a cette valeur.
     */
    public static void setBallSize(int ballsize){
        ballSize = ballsize;
    }

    /**
     * 
     *réinitialise la taille des balles.
     */
    public static void resetSize(){
        ballSize = 10;
    }


}
