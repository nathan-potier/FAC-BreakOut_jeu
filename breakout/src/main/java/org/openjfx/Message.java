package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Message {

    //Méthode pour afficher un message à la fin de jeu
    public static void afficherMessageFinDeJeu(GraphicsContext gc, int score){
        boolean recordBattu = (score >= Score.getBestScore((Pseudo.getPseudo())));
        if(recordBattu){
            afficherMessageVictoire(gc);
        }else{
            afficherMessageDefaite(gc);
        }
    }

    //Méthode pour afficher le message en cas du record battu
    private static void afficherMessageVictoire(GraphicsContext gc) {
        System.out.println("Afficher le message victoire");
        gc.setStroke(Color.GREENYELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Bravo ! NOUVEAU RECORD !", BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 100);
    }

    //Méthode pour afficher le message en cas du défaite
    private static void afficherMessageDefaite(GraphicsContext gc) {
        System.out.println("Afficher le message défaite");
        gc.setStroke(Color.YELLOW);
        gc.setFont(Font.font("Courier New", FontWeight.NORMAL, 25));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("GAME OVER!", BreakOut.getWidth() / 2, BreakOut.getHeight() / 2 - 100);
    }
}

