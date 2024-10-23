package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;



public class Affichage {

    // Coordonnées et taille du champ de texte pour le pseudo
    private static String pseudoInput = "";  // Stocke l'entrée utilisateur temporairement
    private static boolean isEnteringPseudo = true;

    //coordonnées d'affichage du bouton "Retour"
    private static double replayButtonX = BreakOut.getWidth() / 2-50;
    private static double replayButtonY = BreakOut.getHeight() / 2+60; //position du bouton "Retour" sous le meilleur score
    private static double replayButtonWidth = 100;
    private static double replayButtonHeight = 40;
    private static Image backgroundImage = backgroundImage = new Image("file:src/main/resources/Background/Retro_neon.jpg");

    

    
    public static double getBtnX() { return replayButtonX; }
    public static double getBtnY() { return replayButtonY; }
    public static double getBtnWidth() { return replayButtonWidth; }
    public static double getBtnHeight() { return replayButtonHeight; }


    public static void setBackGound(String BackGround){
        String src = "file:src/main/resources/Background/"+ backgroundImage;
        backgroundImage = new Image(src);
    }

    public static void setGC(GraphicsContext gc){




        gc.drawImage(backgroundImage, 0, 0, BreakOut.getWidth(), BreakOut.getHeight());


        gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);
    }


    //Méthode pour afficher la fin du jeu
    public static void afficherFinDeJeu(GraphicsContext gc, int score, Bouton boutonRetour) {
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(25));
        gc.setTextAlign(TextAlignment.CENTER);

        // Message "Game Over" et score
        gc.fillText("Game Over!", BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 30);
        gc.fillText("Score: " + score, BreakOut.getWidth() / 2, BreakOut.getHeight() / 2);
        gc.fillText("Best Score: " + Score.getBestScore(Pseudo.getPseudo()), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 + 30);

        // Afficher le bouton "Retour"
        boutonRetour.afficher(gc);
    }


    // Méthode pour afficher le menu principal
public static void afficherMenuPrincipal(GraphicsContext gc, Bouton boutonJouer) {
    // Affichage du titre du jeu
    afficherTitre(gc);

    // Afficher le champ de texte pour le pseudo
    if (!Pseudo.estDef()){
        afficherChampPseudo(gc);
    }
    

    // Afficher le meilleur score
    afficherMeilleurScore(gc);

    // Afficher le bouton "Jouer"
    afficherBoutonJouer(gc, boutonJouer);

    // Afficher les 5 derniers scores à droite
    afficherDerniersScores(gc);
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
        gc.fillText("Last scores:", scoresX + 10, scoresY + 20);
        
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
        double champPseudoY = BreakOut.getHeight() / 2 - 70;  // Remonter le champ de pseudo
        double textePseudoY = champPseudoY - 10;

        // Afficher le texte "Entrer Pseudo :"
        gc.fillText("Pseudo :", BreakOut.getWidth() / 2, textePseudoY);

        // Ajouter un fond noir pour le champ de saisie
        gc.setFill(Color.BLACK);
        gc.fillRect(BreakOut.getWidth() / 2 - 100, champPseudoY, 200, 30);  // Fond noir du champ de texte

        // Afficher le contour du champ de pseudo (couleur blanche)
        gc.setStroke(Color.WHITE);
        gc.strokeRect(BreakOut.getWidth() / 2 - 100, champPseudoY, 200, 30);

        // Afficher l'entrée utilisateur
        gc.setFill(Color.WHITE);
        gc.fillText(pseudoInput, BreakOut.getWidth() / 2, champPseudoY + 20);
    }

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

    // Méthode pour récupérer le pseudo quand le jeu commence
    public static void enregistrerPseudo() {
        Pseudo.setPseudo(pseudoInput);
        isEnteringPseudo = false;  // Désactiver la saisie après le début du jeu
    }

    public static void resetPseudoInput() {
        pseudoInput = "";
        isEnteringPseudo = true;  // Réactiver la saisie quand on retourne au menu
    }

    


}
