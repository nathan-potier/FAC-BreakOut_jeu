package org.openjfx;

/**
 * La classe MoreBall sert à générer des PowerUp utilisables dans un jeu de Breakout.
 * Il s'agit d'une sous-classe de la classe PowerUp.
 * Le PowerUp s'il est activé ajoute 3 nouvelles balles en jeu.
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

public class MoreBall extends PowerUp{

    private Balle balle1;
    private Balle balle2;
    private Balle balle3;

    
    /**
     * Constructeur d'un PowerUp MoreBall défini par plusieurs paramètres.
     *  
     * @param PUx un double représentant la position X initiale du PowerUp
     * @param PUy un double représentant la position Y initiale du PowerUp
    */
    public MoreBall(double PUx, double PUy, boolean malus) {
        super(PUx, PUy, malus);
    }

    /**
     * Applique les effets du PowerUp.
     * Ajoute 3 nouvelles balles en jeu
     * 
     */
    @Override
    public void applyEffect(){
        this.balle1 = new Balle(BreakOut.getWidth() / 2,BreakOut.getHeight() / 2);
        this.balle2 = new Balle(BreakOut.getWidth() / 2 +10,BreakOut.getHeight() / 2);
        this.balle3 = new Balle(BreakOut.getWidth() / 2 +20,BreakOut.getHeight() / 2);
        balle1.rebondHorizontal(); // les 3 balles prendront une direction différente
        balle2.rebondVertical();
        BreakOut.balles.add(balle1);
        BreakOut.balles.add(balle2);
        BreakOut.balles.add(balle3);
        BreakOut.nbBalle = BreakOut.nbBalle + 3;
    }

    
}
