package org.openjfx;
 import javafx.scene.canvas.GraphicsContext;
 import javafx.scene.paint.Color;
 import javafx.scene.text.TextAlignment;

 public class BoutonPause extends Bouton{
    private boolean isPaused;

    public BoutonPause(double x, double y, double width, double height, String text){
        super(x, y, width, height, "Pause");
        this.isPaused = false; 
    }

    public void togglePaused() {
        isPaused = !isPaused;
        super.setText(isPaused ? "Continuer" : "Pause");
    }

    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public void afficher(GraphicsContext gc){
        super.afficher(gc);
    }

    public void setPaused(boolean b) {
        this.isPaused = b; // 直接设置暂停状态
        if (b) {
            setText("Continuer"); 
        } else {
            setText("Pause"); 
        }
    }
    
}