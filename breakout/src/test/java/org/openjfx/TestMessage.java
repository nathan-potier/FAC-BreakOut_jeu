package org.openjfx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class TestMessage {

    private GraphicsContext gc;
    private Canvas canvas;

    //Configuration avant chaque test
    @BeforeEach
    public void setup(){
        //Initialise le Canvas
        canvas = new Canvas(400, 200);
        gc = canvas.getGraphicsContext2D();
    }

    //Teste si le message retourné indique un nouveau record battu
    @Test
    public void testAfficherMessageFinDeJeu_RecordBattu(){
        String message = Message.afficherMessageFinDeJeu(gc, 600,500);
        assertEquals("Bravo ! NOUVEAU RECORD !", message);
    }

    //Teste si le message retourné indique une égalité
    @Test
    public void testAfficherMessageFinDeJeu_Egalite(){
        String message = Message.afficherMessageFinDeJeu(gc, 500,500);
        assertEquals("Presque! Essayer encore une fois!", message);
    }

    //Teste si le message retourné indique une défaite
    @Test
    public void testAfficherMessageFinDeJeu_RecordNonBattu(){
        String message = Message.afficherMessageFinDeJeu(gc, 400,500);
        assertEquals("GAME OVER!", message);
    }

    //Vérifie le message de victoire est correct
    @Test
    public void testGetMessageVictoire() {
        assertEquals("Bravo ! NOUVEAU RECORD !", Message.getMessageVictoire());
    }

    //Vérifie le message d'égalité est correct
    @Test
    public void testGetMessageEgalite() {
        assertEquals("Presque! Essayer encore une fois!", Message.getMessageEgalite());
    }

    //Vérifie le message de défaite est correct
    @Test
    public void testGetMessageDefaite() {
        assertEquals("GAME OVER!", Message.getMessageDefaite());
    }
    
}
