package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Bouton {

    private double x, y, width, height;
    private String text;
    private Color defaultColor = Color.YELLOW;
    private Color hoverColor = Color.RED;
    private boolean isHover = false;

    public Bouton(double x, double y, double width, double height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void setHover(boolean hover){ this.isHover = hover; }

    // Afficher le bouton sur l'écran
    public void afficher(GraphicsContext gc) {
        //Effet de survol sur le bouton
        if(isHover){
            gc.setStroke(hoverColor);
        }else{
            gc.setStroke(defaultColor);
        }
        
        gc.strokeRect(x, y, width, height);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText(text, x + width / 2, y + height / 2 + 5);
    }

    // Vérifier si un clic est à l'intérieur du bouton
    public boolean estClique(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    // Vérifier si la souris est au-dessus du bouton
    public boolean isHover(double mouseX, double mouseY){
        return estClique(mouseX, mouseY);
    }
    
}
