package org.openjfx;

import java.util.Random;

/**
 * La classe PowerUp sert à générer des PowerUp utilisables dans un jeu de Breakout.
 * Il existe plusieurs types de PowerUp définis dans des sous-classes de celle-ci.
 * 
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul>
 * <li> Renvoie si le PowerUp est entrain de tomber ou non {@link #getTombe())}.</li>
 * <li> Associe au PowerUp si il tombe ou non {@link #setTombe(boolean)}.</li>
 * <li> Retourne la position X du PowerUp {@link #getPux()}.</li>
 * <li> Retourne la position Y du PowerUp {@link #getPuy()}.</li>
 * <li> Actualise la position Y du PowerUp {@link #actuPUy()}.</li>
 * <li> Renvoie la taille de la bille représentant le PowerUp {@link #getSize()}.</li>
 * <li> Applique les effets du PowerUp {@link #applyeffect()}.</li>
 * <li> Renvoie un PowerUp Aléatoire entre les PowerUp disponibles {@link #randomPU(double,double)}.</li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */

public class PowerUp {

    private static int PUsize = 10; 
    private double PUx;
    private double PUy;
    private boolean tombe = false;
    private boolean actif;
    private static double PUyspeed = 1;
    private boolean malus;

    /**
     * Constructeur d'un PowerUp défini par plusieurs paramètres.
     *  
     * @param PUx un double représentant la position X initiale du PowerUp
     * @param PUy un double représentant la position Y initiale du PowerUp
    */
    public PowerUp(double PUx, double PUy, boolean malus){
        this.PUx = PUx;
        this.PUy = PUy;
        this.malus = malus;
    }

    /**
     * Renvoie si le PowerUp est entrain de tomber.
     * 
     * @return true si le PowerUp est entrain de tomber.
     */
    public boolean getTombe(){
        return this.tombe;
    }

    /**
     * @param nbrVie boolean représentant si le PowerUp tombe ou non.
     */
    public void setTombe(boolean tombe){
        this.tombe = tombe;
    }

    /**
     * Renvoie la position X du PowerUp.
     * 
     * @return la position X du PowerUp.
     */
    public  double getPux(){
        return this.PUx;
    }

    /**
     * Renvoie la position Y du PowerUp.
     * 
     * @return la position Y du PowerUp.
     */
    public  double getPuy(){
        return this.PUy;
    }

    /**
     * Actualise la position Y du PowerUp.
     * 
     */
    public void actuPUy(){
        this.PUy += PUyspeed;
    }
    
    /**
     * Renvoie la taille de la bille représenant le PowerUp.
     * 
     * @return la taille de la bille représenant le PowerUp.
     */
    public static double getSize(){
        return PUsize;
    }

    /**
     * Applique les effets du PowerUp.
     * 
     */
    public void applyEffect(){

    }

    /**
     * Renvoie un PowerUp au hasard parmis ceux disponibles.
     * 
     * @return un PowerUp au hasard parmis ceux disponibles.
     */
    public static PowerUp randomPU(double x ,double y){
        Random r = new Random();
        double z = r.nextInt(6);
        if(z == 0){
            return new MoreBall(x,y, false);
        }else if(z == 1){
            return new MoreRacketSize(x,y, false);
        }else if (z==2){
            return new MoreBallSize(x, y, false);
        }else if (z==3){
            return new MalusBallSpeed(x, y, true);
        }else if (z==4){
            return new MalusRacket(x, y, true);
        }else{
            return new TpBalle(x, y, false);
        }
    }

    public void reset(double PUx, double PUy){
        this.PUx = PUx;
        this.PUy = PUy;
        this.actif = false;
        this.tombe = false;
    }
    public boolean getMalus(){
        return this.malus;
    }

    
}
