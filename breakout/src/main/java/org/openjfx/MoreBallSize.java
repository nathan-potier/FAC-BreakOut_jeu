package org.openjfx;

/**
 * La classe MoreBallSize sert à générer des PowerUp utilisables dans un jeu de Breakout.
 * Il s'agit d'une sous-classe de la classe PowerUp.
 * Le PowerUp s'il est activé augmente la taille de toutes les balles en jeu.
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

public class MoreBallSize extends PowerUp{

    
    /**
     * Constructeur d'un PowerUp MoreBallSize défini par plusieurs paramètres.
     *  
     * @param PUx un double représentant la position X initiale du PowerUp
     * @param PUy un double représentant la position Y initiale du PowerUp
    */
    public MoreBallSize(double PUx, double PUy) {
        super(PUx,PUy);
        

    }

    /**
     * Applique les effets du PowerUp.
     * Augmente la taille des balles en jeu
     * 
     */
    public void applyEffect(){
        Balle.setBallSize(50);
    }

    
}
