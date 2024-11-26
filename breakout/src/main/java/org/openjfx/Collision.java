package org.openjfx;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Collision {

    public static int checkCollision(ArrayList<Balle> balles, Niveau niv){
        int add = 0;
        Brick[][] bricks = niv.getAll();
        for (int i = 0; i < niv.getBrickRows(); i++) {
            for (int j = 0; j < 12; j++) {
                if (bricks[i][j].isNotBreak) {
                    double brickX = j * (bricks[i][j].getWidth() + 5);
                    double brickY = i * (bricks[i][j].getHeight() + 5);
            
                    for(int k = 0; k<balles.size(); k++){
                        if (Balle.isTouching(balles.get(k), bricks[i][j], brickX, brickY)) {
                            balles.get(k).rebond();
        
                                           
                            if(bricks[i][j].couleur() != Color.GRAY && bricks[i][j].getNbrVie() == 0){
                                bricks[i][j].isNotBreak = false;
                                niv.brickDetruite();
                                add += bricks[i][j].getScore(); 
                                bricks[i][j].getPowerUp().setTombe(true);

                                Son.stopSonBrique(); // le son des briques cassées
                                Son.jouerSonBrique(); // 

                            }else{
                                bricks[i][j].setNbrVie(bricks[i][j].getNbrVie() - 1);
                            }
                        }
                    }
                }
            }
        }
        return add;
    }
    
}
