package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Affichage {

    //coordonnées d'affichage du bouton "Retour"
    private static double replayButtonX = BreakOut.getWidth() / 2-50;
    private static double replayButtonY = BreakOut.getHeight() / 2+60; //position du bouton "Retour" sous le meilleur score
    private static double replayButtonWidth = 100;
    private static double replayButtonHeight = 40;
    

    
    public static double getBtnX() { return replayButtonX; }
    public static double getBtnY() { return replayButtonY; }
    public static double getBtnWidth() { return replayButtonWidth; }
    public static double getBtnHeight() { return replayButtonHeight; }

    public static void setGC(GraphicsContext gc){
        
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, BreakOut.getWidth(), BreakOut.getHeight());
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
        gc.setFill(Color.WHITE);
    }
    //Méthode pour afficher la fin du jeu
    public static void afficherFinDeJeu(GraphicsContext gc, int score){
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(25));
        gc.setTextAlign(TextAlignment.CENTER);

        //Message perdant
        gc.fillText("Game Over !", BreakOut.getWidth() / 2, BreakOut.getHeight() / 2-30);
        gc.fillText("Score: " + score, BreakOut.getWidth() / 2, BreakOut.getHeight() / 2);
        //int bestScore = Score.getBestScore();
        gc.fillText("Best Score: " + Score.getBestScore(), BreakOut.getWidth() / 2, BreakOut.getHeight() / 2+30);
    }

    //Affiche le bouton "Retour"
    public static void afficherBoutonRetour(GraphicsContext gc){
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight);
        gc.setStroke(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Retour", replayButtonX+replayButtonWidth/2, replayButtonY+replayButtonHeight/2+10);
    }

    public static void MenuPrincipal(GraphicsContext gc, double rectX, double rectY, double rectWidth, double rectHeight){
        gc.setStroke(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Courier New",  FontWeight.LIGHT, 25));
        
        
        gc.strokeText("Jouer", BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 + 6);
        gc.strokeRect(rectX, rectY, rectWidth, rectHeight);

        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 105));
        gc.fillText(BreakOut.getTitre(), BreakOut.getWidth() / 2,110);

        // Afficher le meilleur score
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
        gc.strokeText("Best Score: " + Score.getBestScore(),  BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 + 60);

        // Afficher les 5 derniers scores à droite
        int[] lastFiveScores = Score.getLastFiveScores();

        // Déterminer la largeur et la hauteur du rectangle
        double scoresWidth = 200; // Largeur du rectangle
        double scoresHeight = 180; // Hauteur du rectangle
        double scoresX = BreakOut.getWidth() - scoresWidth - 20; // Position X (20 pixels à partir du bord droit)
        double scoresY = (BreakOut.getHeight() - scoresHeight) / 2; // Centré verticalement

        // Dessiner le contour jaune du rectangle
        gc.setStroke(Color.YELLOW); // Couleur des bords
        gc.strokeRect(scoresX, scoresY, scoresWidth, scoresHeight); // Dessiner le contour

        // Afficher les scores à l'intérieur du rectangle
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gc.setFill(Color.YELLOW); // Couleur du texte
        gc.setTextAlign(TextAlignment.LEFT); // Alignement à gauche

        
        
        // affiche les 5 dernier scores
        gc.fillText("Last scores :", scoresX + 10, scoresY + 20);
        for (int i = 0; i < lastFiveScores.length; i++) {
            gc.fillText("" + lastFiveScores[i], scoresX + 10, scoresY + 50 + (i * 30));
        }
    }
}
