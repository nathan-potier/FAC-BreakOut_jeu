package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * La classe Affichage est responsable de la gestion de l'affichage du jeu BreakOut.
 * Elle gère le rendu graphique, l'affichage des scores, du pseudo, des boutons,
 * ainsi que la gestion des interactions clavier pour la saisie du pseudo.
 * 
 * <p>
 * Les principales fonctionnalités incluent :
 * </p>
 * <ul>
 * <li>Affichage du menu principal avec le meilleur score, les derniers scores,
 * et la saisie du pseudo</li>
 * <li>Affichage de la fin de jeu avec le score actuel et le meilleur score</li>
 * <li>Gestion des boutons de l'interface</li>
 * <li>Gestion de l'entrée utilisateur pour la saisie du pseudo</li>
 * </ul>
 * 
 * <p>
 * Cette classe repose sur l'utilisation de l'API JavaFX pour le rendu graphique.
 * </p>
 * 
 * @author Nathan Potier
 * @version 1.1
 */

public class Affichage {

    // Coordonnées et taille du champ de texte pour le pseudo
    private static String pseudoInput = "";  // Stocke l'entrée utilisateur temporairement
    private static boolean isEnteringPseudo = true;

    private static boolean isPaused = false;  // Vérifier si le jeu est en pause.

    //coordonnées d'affichage du bouton "Retour"
    private static double replayButtonX = BreakOut.getWidth() / 2-50;
    private static double replayButtonY = BreakOut.getHeight() / 2+60; //position du bouton "Retour" sous le meilleur score
    private static double replayButtonWidth = 100;
    private static double replayButtonHeight = 40;
    private static Image backgroundImage = backgroundImage = new Image("file:src/main/resources/Background/Retro_neon.jpg");

    

    /**
     * Retourne la position X du bouton "Retour".
     * 
     * @return La coordonnée X du bouton "Retour".
     */
    public static double getBtnX() { return replayButtonX; }

    /**
     * Retourne la position Y du bouton "Retour".
     * 
     * @return La coordonnée Y du bouton "Retour".
     */
    public static double getBtnY() { return replayButtonY; }
    /**
     * Retourne la largeur du bouton "Retour".
     * 
     * @return La largeur du bouton "Retour".
     */
    public static double getBtnWidth() { return replayButtonWidth; }
    /**
     * Retourne la hauteur du bouton "Retour".
     * 
     * @return La hauteur du bouton "Retour".
     */
    public static double getBtnHeight() { return replayButtonHeight; }


    /**
     * Définit une nouvelle image de fond pour le jeu.
     * 
     * @param BackGround Le chemin de la nouvelle image de fond.
     */
    public static void setBackGound(String BackGround){
        String src = "file:src/main/resources/Background/"+ backgroundImage;
        backgroundImage = new Image(src);
    }


    /**
     * Configure le contexte graphique et dessine l'image de fond.
     * 
     * @param gc Le contexte graphique utilisé pour dessiner.
     */
    public static void setGC(GraphicsContext gc){




        gc.drawImage(backgroundImage, 0, 0, BreakOut.getWidth(), BreakOut.getHeight());


        gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);
    }


    /**
     * Affiche le menu principal, incluant le champ de pseudo, le meilleur score,
     * et les derniers scores du joueur.
     * 
     * @param gc Le contexte graphique pour dessiner l'interface.
     * @param boutonJouer Le bouton permettant de commencer une nouvelle partie.
     */
    // Méthode pour afficher le menu principal
    public static void afficherMenuPrincipal(GraphicsContext gc, Bouton boutonJouer, Bouton boutonSettings, boolean drawSettingsRectangle) {
        // Affichage du titre du jeu
        afficherTitre(gc);
    
        // Afficher le champ de texte pour le pseudo
        if (!Pseudo.estDef()) {
            afficherChampPseudo(gc);
        }
    

        // Afficher le meilleur score
        afficherMeilleurScore(gc);
    
        // Afficher le bouton "Jouer"
        afficherBoutonJouer(gc, boutonJouer);
    
        // Afficher le bouton "Settings"
        afficherBoutonSettings(gc, boutonSettings);  // Appelle la méthode pour afficher le bouton "Settings"
        






        // Afficher les 5 derniers scores à droite
        afficherDerniersScores(gc);
    }

    /**
     * Affiche le bouton pour activer/désactiver la musique de fond.
     * 
     * @param gc Le contexte graphique pour dessiner l'interface.
     * @param boutonToggleBackgroundMusic Le bouton pour activer/désactiver la musique de fond.
     */
    public static void afficherBoutonToggleBackgroundMusic(GraphicsContext gc, Bouton boutonToggleBackgroundMusic) {
        // Configurer les styles du bouton
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 10));
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Afficher le bouton "ToggleBackgroundMusic"
        boutonToggleBackgroundMusic.afficher(gc);
    }

    /**
     * Affiche le bouton pour activer/désactiver les sons de collision.
     * 
     * @param gc Le contexte graphique pour dessiner l'interface.
     * @param boutonToggleCollisionSound Le bouton pour activer/désactiver les sons de collision.
     */
    public static void afficherBoutonToggleCollisionSound(GraphicsContext gc, Bouton boutonToggleCollisionSound) {
        // Configurer les styles du bouton
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 10));
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Afficher le bouton "ToggleCollisionSound"
        boutonToggleCollisionSound.afficher(gc);
    }

    /**
     * Affiche le bouton pour activer/désactiver les sons de rebond.
     * 
     * @param gc Le contexte graphique pour dessiner l'interface.
     * @param boutonToggleBounceSound Le bouton pour activer/désactiver les sons de rebond.
     */
    public static void afficherBoutonToggleBounceSound(GraphicsContext gc, Bouton boutonToggleBounceSound) {
        // Configurer les styles du bouton
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 10));
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Afficher le bouton "Settings"
        boutonToggleBounceSound.afficher(gc);
    }


    private static void afficherBoutonSettings(GraphicsContext gc, Bouton boutonSettings) {
        // Configurer les styles du bouton
        gc.setStroke(Color.YELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 20));
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Afficher le bouton "Settings"
        boutonSettings.afficher(gc);
    }

    // Méthode pour dessiner un rectangle au-dessus du bouton "Settings"
    public static void dessinerRectangle(GraphicsContext gc, Bouton boutonSettings) {
        // Sauvegarder l'état actuel du contexte graphique
        gc.save();

        // Dessiner le rectangle noir
        gc.setFill(Color.color(0, 0, 0, 0.7)); // 0.7 est l'alpha pour un noir légèrement transparent
        gc.fillRect(boutonSettings.getX(), boutonSettings.getY() - 75, 300, 75); // Utiliser la taille souhaitée

        // Dessiner la bordure jaune
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(1); // Ajustez la largeur de la bordure selon vos préférences
        gc.strokeRect(boutonSettings.getX(), boutonSettings.getY() - 75, 300, 75);

        // Rétablir l'état précédent du contexte graphique
        gc.restore();
    }




    // Méthode pour afficher le titre du jeu
    private static void afficherTitre(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 105));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(BreakOut.getTitre(), BreakOut.getWidth() / 2, 110);
    }

    // Méthode pour afficher le meilleur score
    private static void afficherMeilleurScore(GraphicsContext gc) {
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
        gc.setStroke(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Best Score: " + Score.getBestScore(Pseudo.getPseudo()), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 + 60);
    }

    // Méthode pour afficher le bouton "Jouer"
    private static void afficherBoutonJouer(GraphicsContext gc, Bouton boutonJouer) {
        // Configurer les styles du bouton
        gc.setStroke(Color.YELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Afficher le bouton "Jouer"
        boutonJouer.afficher(gc);
    }

 //Méthode pour afficher le boutonMute
 public static void afficherBoutonMute(GraphicsContext gc, Bouton boutonMute) {
    if (!isPaused) {  // En cours de jeu
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(20));
        gc.setTextAlign(TextAlignment.CENTER);
    }
    // Afficher le bouton "Muet"
    boutonMute.afficher(gc);
}

// Méthode pour afficher le boutonPause
public static void afficherBoutonPause(GraphicsContext gc, Bouton boutonPause) {
    if (!isPaused) {  // En cours de jeu
        gc.setStroke(Color.YELLOW);
        gc.setFont(Font.font(20));
        gc.setTextAlign(TextAlignment.CENTER);
    }
    boutonPause.afficher(gc);
}



    // Méthode pour afficher les 5 derniers scores dans un rectangle à droite
    private static void afficherDerniersScores(GraphicsContext gc) {
        int[] lastFiveScores = Score.getLastFiveScores(Pseudo.getPseudo());

        // Déterminer la position et la taille du rectangle
        double scoresWidth = 200;
        double scoresHeight = 180;
        double scoresX = BreakOut.getWidth() - scoresWidth - 20;
        double scoresY = (BreakOut.getHeight() - scoresHeight) / 2;

        // Dessiner le contour du rectangle
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(scoresX, scoresY, scoresWidth, scoresHeight);

        // Afficher les scores à l'intérieur du rectangle
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gc.setFill(Color.YELLOW);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Best scores:", scoresX + 10, scoresY + 20);
        
        for (int i = 0; i < lastFiveScores.length; i++) {
            gc.fillText("" + lastFiveScores[i], scoresX + 10, scoresY + 50 + (i * 30));
        }
    }




    // Méthode pour afficher le champ de texte pour le pseudo
    private static void afficherChampPseudo(GraphicsContext gc) {
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 25));  // Texte "Entrer Pseudo:" plus épais et plus grand
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        
        // Remonter le texte et le champ de pseudo en modifiant les coordonnées Y
        double champPseudoY = BreakOut.getHeight() / 2 - 70;
        double textePseudoY = champPseudoY - 10;

        // Afficher le texte "Entrer Pseudo :"
        gc.fillText("Pseudo :", BreakOut.getWidth() / 2, textePseudoY);

        // Ajouter un fond noir pour le champ de saisie
        gc.setFill(Color.BLACK);
        gc.fillRect(BreakOut.getWidth() / 2 - 100, champPseudoY, 200, 30);

        // Afficher le contour du champ de pseudo (couleur blanche)
        gc.setStroke(Color.WHITE);
        gc.strokeRect(BreakOut.getWidth() / 2 - 100, champPseudoY, 200, 30);

        // Afficher l'entrée utilisateur
        gc.setFill(Color.WHITE);
        gc.fillText(pseudoInput, BreakOut.getWidth() / 2, champPseudoY + 20);
    }

    /**
     * Gère l'entrée utilisateur depuis le clavier pour saisir le pseudo.
     * 
     * @param e L'événement clavier détecté.
     */
    // Méthode pour gérer l'entrée utilisateur depuis le clavier
    public static void handleKeyPress(KeyEvent e) {
        if (isEnteringPseudo) {  // Vérifie si l'on est toujours en mode saisie du pseudo
            if (e.getCode() == KeyCode.BACK_SPACE && pseudoInput.length() > 0) {
                pseudoInput = pseudoInput.substring(0, pseudoInput.length() - 1);
            } else if (e.getText().matches("[A-Za-z ]")) {  // Autoriser seulement les lettres et les espaces
                if (pseudoInput.length() < 15) {  // Limiter la longueur à 15 caractères
                    pseudoInput += e.getText();
                }
            }
        }
    }

    /**
     * Enregistre le pseudo lorsque le jeu commence et désactive la saisie.
     */
    // Méthode pour récupérer le pseudo quand le jeu commence
    public static void enregistrerPseudo() {
        Pseudo.setPseudo(pseudoInput);
        isEnteringPseudo = false;  // Désactiver la saisie après le début du jeu
    }

    /**
     * Réinitialise la saisie du pseudo lorsque le joueur revient au menu principal.
     */
    public static void resetPseudoInput() {
        pseudoInput = "";
        isEnteringPseudo = true;  // Réactiver la saisie quand on retourne au menu
    }



    /**
     * Affiche l'écran de fin de jeu avec le score actuel, le meilleur score et les derniers scores du joueur.
     * 
     * @param gc Le contexte graphique pour dessiner l'interface.
     * @param score Le score actuel du joueur.
     * @param boutonRejouer Le bouton permettant de rejouer le jeu sans retourner au menu principal.
     * @param boutonChangeModeJeu Le bouton permettant de rechoisir le mode de jeu.
     * @param boutonRetour Le bouton permettant de retourner au menu principal.
     * @param boutonSettings Le bouton permettant d'aller dans les paramètres.
     */
    //Méthode pour afficher la fin du jeu
    public static void afficherFinDeJeu(GraphicsContext gc, int score, Bouton boutonRejouer, Bouton boutonChangeMode, Bouton boutonRetour, Bouton boutonSettings, boolean drawSettingsRectangle) {
    
        //Afficher un message à la fin de jeu
        Message.afficherMessageFinDeJeu(gc, score);  

        //Afficher le score actuel
        afficherScoreActuel(gc,score);

        //Afficher le meilleur score
        afficherMeilleurScoreFinDeJeu(gc);

        //Afficher le bouton rejouer
        afficherBoutonRejouer(gc, boutonRejouer);

        //Afficher le bouton change mode de jeu 
        afficherBoutonChangeModeJeu(gc, boutonChangeMode);

        //Afficher le bouton retour
        afficherBoutonRetour(gc, boutonRetour);

        // Afficher le bouton "Settings"
        afficherBoutonSettings(gc, boutonSettings);

        //Réafficher les 5 derniers scores à droite
        afficherDerniersScores(gc);

        
    }

    //Méthode pour afficher le score actuel
    private static void afficherScoreActuel(GraphicsContext gc, int score) {
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
        gc.setStroke(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Score: " + score, BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 50);
    }

    //Méthode pour afficher le meilleur score à la fin de jeu
    private static void afficherMeilleurScoreFinDeJeu(GraphicsContext gc) {
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
        gc.setStroke(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Best Score: " + Score.getBestScore(Pseudo.getPseudo()), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2);
    }

    //Méthode pour afficher le bouton "Rejouer"
    private static void afficherBoutonRejouer(GraphicsContext gc, Bouton boutonRejouer) {
        gc.setStroke(Color.YELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        boutonRejouer.afficher(gc);
    }

    //Méthode pour afficher le bouton "Changer de mode"
    private static void afficherBoutonChangeModeJeu(GraphicsContext gc, Bouton boutonChangerMode) {
        gc.setStroke(Color.YELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        boutonChangerMode.afficher(gc);
    }

    //Méthode pour afficher le bouton "Retour"
    private static void afficherBoutonRetour(GraphicsContext gc, Bouton boutonRetour) {
        gc.setStroke(Color.YELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        boutonRetour.afficher(gc);
    }

    //menu selection mode classique ou personnalisé
    public static void affichageMenuSelection(GraphicsContext gc, Bouton classique, Bouton perso, Bouton création, Bouton retour){
        
        afficherBoutonJouer(gc, classique);
        afficherBoutonJouer(gc, perso);
        afficherBoutonJouer(gc, création);
        retour.afficher(gc);    
    }

    public static void afficheTexte(GraphicsContext gc, String texte){
        gc.setFill(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(texte, BreakOut.getWidth() / 2, (BreakOut.getHeight() / 2) + 150);
    }

    //menu selection mode classique ou personnalisé
    public static void affichageMenuProchainNiveau(GraphicsContext gc, Bouton prochainNiveau, Bouton retour){
        
        afficherBoutonJouer(gc, prochainNiveau);
        retour.afficher(gc);    
    }

}
