package org.openjfx;

import javafx.scene.paint.Color;


/**
 * La classe Brick sert à générer des Briques utilisables dans un jeu de Breakout.
 * Il existe plusieurs types de Briques définis dans des sous-classes de celle-ci.
 * 
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul>
 * <li> Renvoie la couleur associée a la Brique {@link #couleur()}.</li>
 * <li> Retourne la largeur de la Brique {@link #getWidth()}.</li>
 * <li> Retourne la hauteur de la Brique {@link #getHeigth()}.</li>
 * <li> Retourne le score associé a la brique {@link #getScore()}.</li>
 * <li> Retourne le nombre de point de vie de la brique {@link #getNbrVie()}.</li>
 * <li> Associe le nombre de point de vie donné a la brique {@link #setNbrVie(int)}.</li>
 * <li> Associe le PowerUp donné a la brique {@link #setPowerUp(PowerUp)}.</li>
 * <li> Associe un boolean annoncant si la brique contient ou non un PowerUp a cette meme brique{@link #setHavePower(boolean)}.</li>
 * <li> Renvoie si la brique contient un PowerUp ou non {@link #getHavePower()}.</li>
 * <li> Retourne le PowerUp associé a la brique. {@link #getPowerUp()}.</li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */

public class Brick {

    private int nbrVie;
    public boolean isNotBreak;
    private final double brickWidth;
    private final double brickHeight;
    private final int score; 
    private PowerUp powerup;
    private boolean havePower;


    /**
     * Constructeur d'une Brique définie par plusieurs paramètres.
     *  
     * @param isNotBreak un boolean représentant si la brique est cassée ou non
     * @param brickWidth un double représentant la largeur de la brique
     * @param brickHeight un double représentant la hauteur de la brique 
     * @param powerup un PowerUp représenant le PowerUp associée a la brique
    */
    public Brick(boolean isNotBreak, double brickWidth, double brickHeight, PowerUp powerup){
        this.isNotBreak = isNotBreak;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.score = 0;
        this.nbrVie = 0;
        this.powerup = null;
        this.havePower = false;
        this.powerup = powerup;

        

    }
    
    /**
     * Retourne la couleur de la Brique".
     * 
     * @return La couleur de la Brique.
     */
    public Color couleur(){
        return null;
    }

    /**
     * Retourne la largeur de la Brique".
     * 
     * @return La largeur de la Brique.
     */
    public double getWidth(){
        return this.brickWidth;
    }

    /**
     * Retourne la hauteur de la brique.
     * 
     * @return La hauteur de la brique.
     */
    public double getHeight(){
        return this.brickHeight;
    }

    /**
     * Retourne le score associé a la brique.
     * 
     * @return le score associée a la brique.
     */
    public int getScore(){
        return this.score;
    }


    /**
     * Retourne le nombre de point de vie de la brique.
     * 
     * @return le nombre de point de vie de la brique.
     */
    public int getNbrVie(){
        return this.nbrVie;
    }

    /**
     * @param nbrVie entier représentant le nombre de vie voulant être associé a la brique.
     */
    public void setNbrVie(int nbrVie) {
        this.nbrVie = nbrVie;
    }

    /**
     * 
     * @param p PowerUp représentant le PowerUp voulant être associé a la brique.
     */
    public void setPowerUp(PowerUp p){
        this.powerup = null;
    }

    /**
     * 
     * @param havePower boolean annoncant si la brique contient ou non un PowerUp.
     */
    public void setHavePower(boolean havePower){
        this.havePower = havePower;
    }

    /**
     * Retourne vrai si la brique contient un PowerUp sinon faux
     * 
     * @return true si la brique contient un Powerup sinon false.
     */
    public boolean getHavePower(){
        return this.havePower;
    }
    /**
     * Retourne le PowerUp associé a la brique.
     * 
     * @return le PowerUp associé a la brique.
     */
    public PowerUp getPowerUp(){
        return this.powerup;
    }
}
