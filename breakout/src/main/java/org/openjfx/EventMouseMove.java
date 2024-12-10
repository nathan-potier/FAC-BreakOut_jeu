package org.openjfx;

import javafx.scene.canvas.GraphicsContext;

import org.openjfx.Bouton;
import org.openjfx.GameState;
import org.openjfx.Racket;

import javafx.scene.canvas.Canvas;


public class EventMouseMove implements EventTrigger{

    public static void actOnInput(Canvas canvas, GraphicsContext gc){
        canvas.setOnMouseMoved(e ->{
            double mouseX = e.getX();
            double mouseY = e.getY();

            for (Bouton bouton : Bouton.getAllButtons()) {
                bouton.setHover(bouton.isHover(mouseX, mouseY));
            }
            
            if (GameState.isMouseMode) {
                Racket.setXpos(mouseX);
            }
        });
    }
    
}
