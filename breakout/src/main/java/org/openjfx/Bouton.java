package org.openjfx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * La classe Bouton représente un bouton graphique interactif dans le jeu.
 * 
 * <p>
 * Chaque instance de la classe Bouton est définie par ses coordonnées, sa
 * largeur, sa hauteur, et le texte affiché dessus.
 * </p>
 * 
 * <p>
 * Cette classe offre des méthodes pour :
 * </p>
 * 
 * <ul>
 * <li>Afficher le bouton sur l'écran avec {@link #afficher(GraphicsContext)}.</li>
 * <li>Vérifier si le bouton est cliqué avec {@link #estClique(double, double)}.</li>
 * </ul>
 * 
 * <p>
 * Les boutons sont stylisés en jaune et affichent leur texte centré.
 * </p>
 * 
 * @version 1.1
 */
public class Bouton {

    private double x, y, width, height;
    private String text;
    private Color defaultColor = Color.YELLOW;
    private Color hoverColor = Color.RED;
    private boolean isHover = false;

    /**
     * Constructeur de la classe Bouton.
     * 
     * @param x      la coordonnée X du coin supérieur gauche du bouton
     * @param y      la coordonnée Y du coin supérieur gauche du bouton
     * @param width  la largeur du bouton
     * @param height la hauteur du bouton
     * @param text   le texte à afficher sur le bouton
     */
    public Bouton(double x, double y, double width, double height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    /**
     * Retourne la coordonnée X du coin supérieur gauche du bouton.
     * 
     * @return la coordonnée X
     */
    public double getX() { return x; }

    /**
     * Retourne la coordonnée Y du coin supérieur gauche du bouton.
     * 
     * @return la coordonnée Y
     */
    public double getY() { return y; }

    /**
     * Retourne la largeur du bouton.
     * 
     * @return la largeur du bouton
     */
    public double getWidth() { return width; }

    /**
     * Retourne la hauteur du bouton.
     * 
     * @return la hauteur du bouton
     */
    public double getHeight() { return height; }

    /**
     * Définit l'état de survol du bouton.
     * 
     * @param hover vrai si le bouton est survolé sinon faux
     */
    public void setHover(boolean hover){ this.isHover = hover; }

    /**
     * Affiche le bouton sur l'écran.
     * 
     * <p>
     * Le bouton est dessiné en utilisant un rectangle jaune avec son texte centré.
     * Si le bouton est survolé, sa couleur de contour change.
     * </p>
     * 
     * @param gc le contexte graphique dans lequel le bouton est dessiné
     */
    public void afficher(GraphicsContext gc) {
        if(isHover){
            gc.setStroke(hoverColor);
        }else{
            gc.setStroke(defaultColor);
        }
        
        gc.strokeRect(x, y, width, height);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText(text, x + width / 2, y + height / 2 + 5);
    }

    /**
     * Vérifie si le bouton a été cliqué en fonction des coordonnées du clic de la souris.
     * 
     * @param mouseX la coordonnée X du clic de la souris
     * @param mouseY la coordonnée Y du clic de la souris
     * @return {@code true} si le clic est à l'intérieur du bouton, sinon {@code false}
     */
    public boolean estClique(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    // Vérifier si la souris est au-dessus du bouton
    public boolean isHover(double mouseX, double mouseY){
        return estClique(mouseX, mouseY);
    }
    
}
