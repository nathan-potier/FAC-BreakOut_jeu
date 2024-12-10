package org.openjfx;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.canvas.Canvas;

class EventKeyPress implements EventTrigger{


    public static void actOnInput(Canvas canvas, GraphicsContext gc){
        canvas.setOnKeyPressed(e -> {

        
            // Gestion de la saisie du pseudo
            if (!Pseudo.estDef()) {
                Affichage.handleKeyPress(e); // Appel de la méthode pour la gestion du pseudo
                
            }
        
            // Événement du clavier pour mettre le jeu en pause avec la touche ESC
            if (GameState.isKeyboardMode || (GameState.isMouseMode && GameState.isKeyboardMode)) {
                if (GameState.isGameStarted && e.getCode() == KeyCode.ESCAPE) {
                    GameState.isPaused = !GameState.isPaused; // Bascule l'état de la pause
                }
                // Flèche droite (déplacer vers la droite)
                if (e.getCode() == KeyCode.RIGHT) {
                    if (Racket.getXPos() + Racket.getWidth() < GameSpec.width) { // Empêcher la palette de dépasser la limite droite de l'écran
                        Racket.setXpos(Racket.getXPos() + 50); // Déplacer vers la droite
                    }
                // Flèche gauche (déplacer vers la gauche)
                } else if (e.getCode() == KeyCode.LEFT) {
                    if (Racket.getXPos() > 0) { // Empêcher la palette de dépasser la limite gauche de l'écran
                        Racket.setXpos(Racket.getXPos() - 50); // Déplacer vers la gauche
                    }
                }
            }
        });
    }






}