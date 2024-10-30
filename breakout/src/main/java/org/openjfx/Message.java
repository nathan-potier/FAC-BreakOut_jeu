package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * La classe {@code Message} est responsable de l'affichage des messages
 * à la fin du jeu, que soit pour une victoire en cas d'un record battu
 * ou pour une défaite.
 * <p>
 * Cette classe utilise un contexte graphique pour dessiner les messages sur l'écran de la fin de jeu.
 * Elle offre des méthodes pour:
 * </p>
 * 
 * <ul>
 * <li>Afficher un message à la fin de jeu en fonction du score obtenu par le joueur avec {@link #afficherMessageFinDeJeu(GraphicsContext gc, int score)}.</li>
 * <li>Afficher un message de victoire avec {@link #afficherMessageVictoire(GraphicsContext)}</li>
 * <li>Afficher un message de défaite avec {@link #afficherMessageDefaite(GraphicsContext)}</li>
 * </ul>
 * 
 * @author Makiya NAKPLONG
 */

public class Message {

    /**
     * Affiche un message de fin de jeu en fonction du score obtenu.
     * 
     * @param gc Le contexte graphique pour dessiner le message.
     * @param score Le score actuel du joueur à la fin du jeu.
     *              Si le score est supérieur au meilleur score précédent un message de victoire est affiché, sinon un message de défaite.
     */
    //Méthode pour afficher un message à la fin de jeu
    public static void afficherMessageFinDeJeu(GraphicsContext gc, int score){
        boolean recordBattu = (score >= Score.getBestScore((Pseudo.getPseudo())));
        if(recordBattu){
            afficherMessageVictoire(gc);
        }else{
            afficherMessageDefaite(gc);
        }
    }

    /**
     * Affiche un message en cas de record battu.
     * 
     * @param gc Le contexte graphique pour dessiner le message de victoire.
     */
    //Méthode pour afficher un message en cas du record battu
    private static void afficherMessageVictoire(GraphicsContext gc) {
        gc.setStroke(Color.GREENYELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Bravo ! NOUVEAU RECORD !", BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 100);
    }

    /**
     * Affiche un message en cas de défaite
     * 
     * @param gc Le contexte graphique pour dessiner le message de défaite.
     */
    //Méthode pour afficher un message en cas du défaite
    private static void afficherMessageDefaite(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("GAME OVER!", BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 100);
    }
}

