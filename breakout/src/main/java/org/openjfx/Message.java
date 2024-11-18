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
 * La classe fournit les méthodes suivantes :
 * </p>
 * 
 * <ul>
 * <li>Afficher un message à la fin de jeu en fonction du score obtenu et du dernier record du joueur avec {@link #afficherMessageFinDeJeu(GraphicsContext gc, int score)}.</li>
 * <li>Retourne le message de victoire à afficher lorsque le joueur bat son record {@link #getMessageVictoire()}.</li>
 * <li>Retourne le message d'égalité à afficher lorsque le score actuel du joueur égale son dernier record {@link #getMessageEgalite()}.</li>
 * <li>Retourne le message de défaite à afficher lorsque le score actuel du joueur est inférieur à son record {@link #getMessageDefaite()}</li>
 * </ul>
 * 
 * <p> {@code GraphicsContext} Cette classe permet également de dessiner les messages sur l'écran à l'aide du contexte graphique:
 * </p>
 * 
 * <ul>
 * <li>Afficher un message de victoire {@link #afficherMessageVictoire(GraphicsContext)}</li>
 * <li>Afficher un message d'égalité {@link #afficherMessageEgalite(GraphicsContext)}</li>
 * <li>Afficher un message de défaite {@link #afficherMessageDefaite(GraphicsContext)}</li>
 * </ul>
 * 
 * @author Makiya NAKPLONG
 * @version 1.0
 */

public class Message {

    /**
     * Affiche un message de fin de jeu en fonction du score obtenu par rapport à son dernier score.
     * 
     * @param gc Le contexte graphique pour dessiner le message.
     * @param score Le score actuel du joueur à la fin du jeu.
     *              Si le score est supérieur au record un message de victoire est affiché, en cas d'une égalité un message d'égalité est affiché sinon un message de défaite.
     * @param dernierRecord Le dernier record du joueur.
     * @return Une chaîne de caractères représentant le message approprié ("victoire", "égalité" ou "défaite")
     */
    //Méthode pour afficher un message à la fin de jeu
    public static String afficherMessageFinDeJeu(GraphicsContext gc, int score, int dernierRecord){
        if(score > dernierRecord){
            afficherMessageVictoire(gc);
            return getMessageVictoire();
        }else if(score == dernierRecord){
            afficherMessageEgalite(gc);
            return getMessageEgalite();
        }else{
            afficherMessageDefaite(gc);
            return getMessageDefaite();
        }
    }

    /**
     * Retourne un message en cas de record battu.
     * 
     * @return Le message de victoire.
     */
    public static String getMessageVictoire(){ return "Bravo ! NOUVEAU RECORD !"; }

    /**
     * Retourne un message d'égalité.
     * 
     * @return Le message d'égalité.
     */
    public static String getMessageEgalite(){ return "Presque! Essayer encore une fois!"; }

    /**
     * Retourne un message en cas de défaite.
     * 
     * @return Le message de défaite.
     */
    public static String getMessageDefaite(){ return "GAME OVER!"; }



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
        gc.strokeText(Message.getMessageVictoire(), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 100);
    }

    /**
     * Affiche un message en cas d'égalité
     *
     * @param gc Le contexte graphique pour dessiner le message d'égalité.
     */
    //Méthode pour afficher un message en cas du record battu
    private static void afficherMessageEgalite(GraphicsContext gc) {
        gc.setStroke(Color.ORANGE);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText(Message.getMessageEgalite(), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 100);
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
        gc.strokeText(Message.getMessageDefaite(), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 100);
    }
}

