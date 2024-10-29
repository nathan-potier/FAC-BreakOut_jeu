package org.openjfx;
 import javafx.scene.canvas.GraphicsContext;
 import javafx.scene.paint.Color;
 import javafx.scene.text.TextAlignment;

 public class BoutonMusique extends Bouton{
    private boolean isMuted;

    public BoutonMusique(double x, double y, double width, double height, String text){
        super(x, y, width, height, "Muet");
        this.isMuted = false; 
    }

    //affichage de bouton
    public void toggleMuted() { 
        isMuted = !isMuted;
        super.setText(isMuted ? "Son activé" : "Muet");
    }

    public boolean isMuted() {
        return isMuted;
    }

    @Override
    public void afficher(GraphicsContext gc){
        super.afficher(gc);
    }
}