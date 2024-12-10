package org.openjfx;
import java.util.Random;
import java.util.ArrayList;


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
 * <li> Retourne la présence ou non d'un power up actif {@link #isActive()}.<\li>
 * <li> Retourne vrai tant que la timer n'a pas atteint un tant donnée {@link #isTimeUp(double)}.<\li>
 * <li> Reinitialise a ses valeurs initiales les parametres de la balle {@link #reset()}.</li>
 * <li> Reinitialise une balle et un array de balle à leur valeur initiales {@link #resetBalles(Balle, ArrayList)}.<\li>
 * <li> Fait rebondir la balle {@link #rebond()}.</li>
 * <li> Met a jour la position X de la balle {@link #MAJPosHorizontal()}.</li>
 * <li> Met a jour la postion Y de la balle {@link #MAJPosVertical()}.</li>
 * <li> Met la taille des balles au int donner {@link #setBallSize(int)}.</li>
 * <li> Réinitialise la taille des balles {@link #resetSize()}.</li>
 * <li> regarde si la balle touche une brique donnée {@link #isTouching(Balle, Brick, int, int)}.<\li>
 * </ul>
 * 
 * @author Noa Gregoire-Mellier
 * @version 1.0
 * 
 */



public class Balle implements HavePowerUp{
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
        this.ballYSpeed = Math.abs((int) Math.round(speedNorm * Math.sin(angle)));


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

    public void setYSpeed(int YSpeed){
        ballYSpeed = YSpeed;
    }
    /**
     * Retourne la vitesse en X de la balle.
     * 
     * @return la vitesse en X de la balle.
     */
    public int getXSpeed(){ return this.ballXSpeed; }

    public void setXSpeed(int XSpeed){
        ballXSpeed = XSpeed;
    }
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
     * Retourne si la balle a un power up appliqué ou non.    
     * 
     * @return la présence ou non d'un power up.
     */
    public boolean isActive(){return Balle.getSize() == 50;}


    public boolean isTimeUp(double timer){return timer >= 700;}

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
     * réinitialise les paramètres de la balle et la liste des balles pour ne contenir qu'une balle
     * 
     * @param balle     balle initial 
     * @param balleArray    liste des balles
     */
    public static void resetBalles(Balle balle){
        balle.reset();
        NiveauActuel.balles = new ArrayList<>();
        NiveauActuel.balles.add(balle);
    }

    /**
     * 
     *fait rebondir la balle.
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

    public double updateTimer(double timer){
        if (isActive()){
            timer++;
            if (isTimeUp(timer)){
                Balle.resetSize();
                return 0;
            }
        }
        return timer;
    }

    /**
     * 
     *réinitialise la taille des balles.
     */
    public static void resetSize(){
        ballSize = 10;
    }


    public static void resetSpeed(){
        for(int i = 0; NiveauActuel.balles.size() > i; i++){
            NiveauActuel.balles.get(i).setXSpeed(NiveauActuel.balles.get(i).getXSpeed()/2);
            NiveauActuel.balles.get(i).setYSpeed(NiveauActuel.balles.get(i).getYSpeed()/2);
        }
    }

    /**
     *  regarde si la balle touche la brique courante.
     * 
     * @param balle  balle courante
     * @param brick  brick courante
     * @param brickX    coordonnée X du point en haut à gauche de la brique courante
     * @param brickY    coordonnée Y du point en haut à gauche de la brique courante
     * @return  booleen qui vérifie su une balle touche la brique courante
     */
    public static boolean isTouching(Balle balle, Brick brick, double brickX, double brickY){
         return balle.getXPos() + Balle.getSize() >= brickX &&
                            balle.getXPos() <= brickX + brick.getWidth() &&
                            balle.getYPos() + Balle.getSize() >= brickY + 5 &&
                            balle.getYPos() <= brickY + brick.getHeight() + 5;
    }


}
