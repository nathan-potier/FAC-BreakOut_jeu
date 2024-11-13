package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;


public class Affichage {

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
        gc.fillText("Best Score: " + Score.getBestScore(), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 + 30);

        // Afficher le bouton "Retour"
        boutonRetour.afficher(gc);
    }


    // Méthode pour afficher le menu principal
public static void afficherMenuPrincipal(GraphicsContext gc, Bouton boutonJouer) {
    // Affichage du titre du jeu
    afficherTitre(gc);

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
    gc.strokeText("Best Score: " + Score.getBestScore(), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 + 60);
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
        int[] lastFiveScores = Score.getLastFiveScores();

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
}
