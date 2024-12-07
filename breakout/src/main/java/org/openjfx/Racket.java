package org.openjfx;

/**
 * La classe Racket sert à générer une Raquette pour un jeu de breakout.
 * 
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul>
 * <li> Modifie la position X de la raquette au double donné {@link #setXpos(Double)}.</li>
 * <li> Renvoie la hauteur de la raquette {@link #getHeight()}.</li>
 * <li> Retourne la largeur de la raquette {@link #getWidth()}.</li>
 * <li> Retourne la position X de la raquette {@link #getXPos()}.</li>
 * <li> Renvoie la position Y de la raquette {@link #getYPos()}.</li>
 * <li> Reinitialise a ses valeurs initiales les parametres de la balle {@link #setRacketWidth(int)}.</li>
 * <li> Fait rebondir verticalement la balle {@link #resetSize())}.</li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */

public class Racket {
    private static int racketWidth = 100;
    private static int racketHeight = 10;
    private static double racketXPos = BreakOut.getWidth() / 2;
    private static double racketYPos = BreakOut.getHeight() - 40;

     /**
     * 
     *@param Xpos met la raquette a la position Xpos.
     */
    public static void setXpos(Double Xpos){ racketXPos = Xpos; }

    /**
     * Retourne la hauteur de la raquette.
     * 
     * @return la hauteur de la raquette.
     */
    public static int getHeight() { return racketHeight ;}

    /**
     * Retourne la largeur de la raquette.
     * 
     * @return la largeur de la raquette.
     */
    public static int getWidth()  { return racketWidth ;}

    /**
     * Retourne la position en X de la raquette.
     * 
     * @return la position en X de la raquette.
     */
    public static double getXPos(){ return racketXPos ;}

    /**
     * Retourne la position en Y de la raquette.
     * 
     * @return la position en Y de la raquette.
     */
    public static double getYPos(){ return racketYPos ;}

    /**
     * 
     *@param width modifie la largeur de la raquette au int donné.
     */
    public static void setRacketWidth(int width){
        racketWidth = width;
    }

    /**
     * 
     *réinitialise la largeur de la raquette au paramètre initial.
     */
    public static void resetSize(){
        racketWidth = 100;
    }
}

