package org.openjfx;

public class BrickVide extends Brick{

    private final int score;
    private int nbrVie;

    /**
     * Constructeur d'une BrickVide définie par plusieurs paramètres.
     *  
     * @param isNotBreak un boolean représentant si la brique est cassée ou non
     * @param brickWidth un double représentant la largeur de la brique
     * @param brickHeight un double représentant la hauteur de la brique 
     * @param powerup un PowerUp représenant le PowerUp associée a la brique
    */
    public BrickVide(boolean isNotBreak, double brickWidth, double brickHeight, PowerUp powerup){
        super(isNotBreak, brickWidth, brickHeight, powerup);
        this.score = 0;
        setHavePower(false);
        this.nbrVie = -1;
        

    }

    public int getNbrVie(){
        return this.nbrVie;
    }


    
}
