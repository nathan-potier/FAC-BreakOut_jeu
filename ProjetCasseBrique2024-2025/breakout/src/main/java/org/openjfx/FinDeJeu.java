package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class FinDeJeu {
    private int width;
    private int height;
    private int bestScore;

    public FinDeJeu(int width, int height, int bestScore){
        this.width = width;
        this.height = height;
        this.bestScore = bestScore;
    }

    //Méthode pour afficher la fin du jeu
    public void afficherFinDeJeu(GraphicsContext gc, int score){
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font(25));
        gc.setTextAlign(TextAlignment.CENTER);

        //Message perdant
        gc.fillText("Game Over !", width/2, height/2-30);
        gc.fillText("Score: " + score, width/2, height/2);

        //Mettre à jour + afficher le meilleur score
        if(score > this.bestScore){
            Score.saveScoreToFile(score);
            this.bestScore = score;
        }
        gc.fillText("Best Score: " + bestScore, width/2, height/2+30);
    }

    //Affiche le bouton "Retour"
    public void afficherBoutonRetour(GraphicsContext gc, double replayButtonX, double replayButtonY, double replayButtonWidth, double replayButtonHeight){
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(replayButtonX, replayButtonY, replayButtonWidth, replayButtonHeight);
        gc.setStroke(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Retour", replayButtonX+replayButtonWidth/2, replayButtonY+replayButtonHeight/2+10);
    }

    //Méthode qui vérifie si le joueur cliquer sur le bouton "Retour"
    public boolean verifierCliqueRetour(double mouseX, double mouseY, double replayButtonX, double replayButtonY, double replayButtonWidth, double replayButtonHeight){
        boolean clicked = (mouseX >= replayButtonX && mouseX <= replayButtonX + replayButtonWidth && mouseY >= replayButtonY && mouseY <= replayButtonY + replayButtonHeight);
        System.out.println("Clicked " + clicked); //juste pour vérifier si le joueur a bien cliqué sur le bouton
        return clicked;
    }
}