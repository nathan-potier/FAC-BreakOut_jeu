package org.openjfx;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class NiveauActuel {

    static int diff;
    static Brick[][] tabBrick;
    static int brickRows;
    static final int brickCols = 12;
    static String modeActif;
    static Niveau niv;
    static int score = 0;
    static int nbBalle = 1;
    static ArrayList<Balle> balles = new ArrayList<>();
    static final Balle balle1 = new Balle(BreakOut.getWidth() / 2,BreakOut.getHeight() / 2);
    static boolean type = true;


    //Réinitialise l'état du jeu pour recommencer une nouvelle partie
    static void resetGame(GraphicsContext gc){

        GameState.isGameOver = false;
        GameState.isGameStarted = false;
        score = 0;
        nbBalle = 1;
        Balle.resetBalles(balle1);
        Racket.resetSize();
        Brick.resetBrickTab(NiveauActuel.tabBrick, NiveauActuel.brickRows, NiveauActuel.brickCols);
        Son.rejouerMusique();
        Affichage.afficherDefFont(gc);
    }
    
}
