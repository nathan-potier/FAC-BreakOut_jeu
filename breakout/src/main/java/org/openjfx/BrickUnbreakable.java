package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * La classe BrickUnbreakable sert à générer des Briques utilisables dans un jeu de Breakout.
 * Il s'agit d'une sous-classe de la classe Brick.
 * La Brique est incassable, ne donne aucun point et ne possède pas de PowerUp.
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul>
 * <li> Renvoie la couleur associée a la Brique {@link #couleur()}.</li>
 * <li> Retourne le score associé a la brique {@link #getScore()}.</li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */

public class BrickUnbreakable extends Brick{

    private final int score;
    public Image imageUnbreakable;

    /**
     * Constructeur d'une BrickUnbreakable définie par plusieurs paramètres.
     *  
     * @param isNotBreak un boolean représentant si la brique est cassée ou non
     * @param brickWidth un double représentant la largeur de la brique
     * @param brickHeight un double représentant la hauteur de la brique 
     * @param powerup un PowerUp représenant le PowerUp associée a la brique
    */
    public BrickUnbreakable(boolean isNotBreak, double brickWidth, double brickHeight, PowerUp powerup){
        super(isNotBreak, brickWidth, brickHeight, powerup);
        this.score = 10;
        setHavePower(false);
        this.imageUnbreakable = new Image("file:src/main/resources/Bricks/Incassable.png"); // Chemin vers l'image de la brique incassable
        

    }

    /**
     * Retourne la couleur de la Brique".
     * 
     * @return La couleur de la Brique.
     */
    public Color couleur(){
        return Color.GRAY;
    }

    /**
     * Retourne le score associé a la Brique".
     * 
     * @return le score associé a la Brique.
     */
    public int getScore(){
        return this.score;
    }

    
    
}
