package org.openjfx;

/**
 * La classe MoreRacketSize sert à générer des PowerUp utilisables dans un jeu de Breakout.
 * Il s'agit d'une sous-classe de la classe PowerUp.
 * Le PowerUp s'il est activé augmente légèrement la taille de la raquette.
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul>
 * <li> Applique les effets du PowerUp {@link #applyEffect()}.</li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */

public class MoreRacketSize extends PowerUp {

    /**
     * Constructeur d'un PowerUp MoreRacketSize défini par plusieurs paramètres.
     *  
     * @param PUx un double représentant la position X initiale du PowerUp
     * @param PUy un double représentant la position Y initiale du PowerUp
    */
    public MoreRacketSize(double Pux, double PUy) {
        super(Pux, PUy);
    }


    /**
     * Applique les effets du PowerUp.
     * Augmente la taille de la Raquette par 2
     * 
     */
    @Override
    public void applyEffect(){
        Racket.setRacketWidth(Racket.getWidth()*2);
    }


    


    
}
