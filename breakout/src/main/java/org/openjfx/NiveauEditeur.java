package org.openjfx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;


/**
 * La classe NiveauEditeur hérite de la classe NiveauInput et sert à laisser 
 * au joueur la possibilité de crée son propre niveau au sein du jeu.
 * 
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * 
 */

public class NiveauEditeur extends NiveauInput{



    NiveauEditeur(){
        super();
        defBrick();
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                setBrick(i, j, new BrickBleue(true, 80, 20, new PowerUp(j * 85, i * 25)));
            }
        }
        setBrickRows(12);
    }

    public int xCoord(double clickX){

        for(int i = 0; i < 12; i++){
            if (clickX >= (i * (80 + 5)) && clickX <= (i * (80 + 5)) + 80){
                return i;
            }
        }
        return 25;
    }

    public int yCoord(double clickY){

        for(int i = 0; i < 12; i++){
            if (clickY >= (i * (20 + 5) + 20) && clickY <= (i * (20 + 5)) + 40){
                return i;
            }
        }
        return 25;
    }

    public void genNiveau(double clickX, double clickY){

        int y = xCoord(clickX);
        int x = yCoord(clickY);
        if (x != 25 && y != 25){    
            
            if (getBrick(x, y) instanceof BrickBleue && getBrick(x,y).isNotBreak){
                setBrick(x, y, new BrickDouble(true, 80, 20, PowerUp.randomPU(y, x)));
            } 
            else if (getBrick(x, y) instanceof BrickDouble){
                setBrick(x, y, new BrickRouge(true, 80, 20, PowerUp.randomPU(y, x)));
            }
            else if (getBrick(x, y) instanceof BrickRouge){
                setBrick(x, y, new BrickUnbreakable(true, 80, 20, PowerUp.randomPU(y, x)));
            }
            else if (getBrick(x, y) instanceof BrickUnbreakable){
                setBrick(x, y, new BrickVide(false, 80, 20, PowerUp.randomPU(y, x)));
            }
            else if (getBrick(x, y) instanceof BrickVide){
                setBrick(x, y, new BrickBleue(true, 80, 20, PowerUp.randomPU(y, x)));
            }
            
        }
        return;
    }


}