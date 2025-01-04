package org.openjfx;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * La classe BrickDouble sert à générer des Briques utilisables dans un jeu de Breakout.
 * Il s'agit d'une sous-classe de la classe Brick.
 * La Brique possède 2 vies, donne 30 points et possède un PowerUp.
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul>
 * <li> Renvoie la couleur associée a la Brique {@link #couleur()}.</li>
 * <li> Retourne le score associé a la brique {@link #getScore()}.</li>
 * <li> Retourne le nombre de point de vie de la brique {@link #getNbrVie()}.</li>
 * <li> Associe le nombre de point de vie donné a la brique {@link #setNbrVie(int)}.</li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */

public class BrickDouble extends Brick {


    private int nbrVie;
    private final int score;
    public Image imageAct;
    public Image imageVerte;
    public Image imageVerte2;

    /**
     * Constructeur d'une BrickDouble définie par plusieurs paramètres.
     *  
     * @param isNotBreak un boolean représentant si la brique est cassée ou non
     * @param brickWidth un double représentant la largeur de la brique
     * @param brickHeight un double représentant la hauteur de la brique 
     * @param powerup un PowerUp représenant le PowerUp associée a la brique
    */
    public BrickDouble(boolean isNotBreak, double brickWidth, double brickHeight, PowerUp powerup){
        super(isNotBreak, brickWidth, brickHeight,powerup);
        this.score = 30;
        this.nbrVie = 1;
        setHavePower(true);
        initialiserImage();
        this.imageAct = imageVerte;
    }

    public void initialiserImage(){
        try {
            this.imageVerte = new Image("file:src/main/resources/Bricks/Verte.png");
            this.imageVerte2 = new Image("file:src/main/resources/Bricks/Verte2.png");


        } catch (Exception e) {
            System.out.print("cela ne marche pas");
        }
    }

    /**
     * Retourne la couleur de la Brique".
     * 
     * @return La couleur de la Brique.
     */
    public Color couleur(){
        return Color.GREEN;
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
     * 
     * @param nbrVie entier représentant le nombre de vie voulant être associé a la brique.
     */
    public void setNbrVie(int nbrVie) {
        this.nbrVie = nbrVie;

        // Change l'image lorsque la vie passe à 0
        if (this.nbrVie == 0) {
            this.imageAct = this.imageVerte2;
        }
        if(nbrVie == 1){
            this.imageAct = this.imageVerte;
        }
    }


}
