package org.openjfx;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

class TestBouton {

    private Bouton bouton;
    private Canvas canvas;
    private GraphicsContext gc;

    // Configuration avant chaque test
    @BeforeEach
    void setUp() {
        bouton = new Bouton(50, 50, 100, 50, "Play"); // Création d'un bouton avec des coordonnées (50, 50), une taille de (100, 50) et le texte "Play"
        canvas = new Canvas(200, 200); // canvas de 200x200
        gc = canvas.getGraphicsContext2D();
    }

    // Vérifie que la position X est correcte
    @Test
    void testGetX() {
        assertEquals(50, bouton.getX());
    }

    // Vérifie que la position Y est correcte
    @Test
    void testGetY() {
        assertEquals(50, bouton.getY()); 
    }

    // Vérifie que la largeur est correcte
    @Test
    void testGetWidth() {
        assertEquals(100, bouton.getWidth()); 
    }

    // Vérifie que la hauteur est correcte
    @Test
    void testGetHeight() {
        assertEquals(50, bouton.getHeight()); 
    }

    // Vérifie que le clic est détecté à l'intérieur du bouton et n'est pas détecté à l'extérieur du bouton
    @Test
    void testEstClique() {
        assertTrue(bouton.estClique(75, 75)); 
        assertFalse(bouton.estClique(10, 10));
    }

    // Test de la méthode setHover
    @Test
    void testSetHover() {
        bouton.setHover(true);
        assertTrue(bouton.isHover(75, 75)); // Vérifie que le survol est détecté à l'intérieur du bouton

        bouton.setHover(false);
        assertFalse(bouton.isHover(10, 10)); // Vérifie que le survol n'est pas détecté à l'extérieur du bouton
    }

    // Test de l'affichage de la couleur en fonction du survol
    @Test
    void testAffichageCouleurSurvol() {
        bouton.setHover(false);
        bouton.afficher(gc);
        assertEquals(bouton.getDefaultColor(), gc.getStroke());

        bouton.setHover(true);
        bouton.afficher(gc);
        assertEquals(bouton.getHoverColor(), gc.getStroke());
    }

    // Vérifie que le texte affiché est correcte
    @Test
    void testAffichageText() {
        bouton.afficher(gc);
        assertEquals("Play", bouton.getText());
    }

    // Vérifie que l'alignement du texte du bouton est centré
    @Test
    void testAlignementTexte() {
        bouton.afficher(gc);
        assertEquals(TextAlignment.CENTER, gc.getTextAlign());
    }
}
