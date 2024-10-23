package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Bouton {

    private double x, y, width, height;
    private String text;

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
    public String getText() { return text; }
    public void setText(String text) { this.text=text; }

    // Afficher le bouton sur l'écran
    public void afficher(GraphicsContext gc) {
        gc.setStroke(Color.YELLOW);
        gc.strokeRect(x, y, width, height);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText(text, x + width / 2, y + height / 2 + 5);
    }

    // Vérifier si un clic est à l'intérieur du bouton
    public boolean estClique(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
