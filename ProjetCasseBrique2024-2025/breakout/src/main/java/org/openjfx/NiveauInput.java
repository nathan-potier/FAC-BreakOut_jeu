package org.openjfx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;


public class NiveauInput extends Niveau{

    private String path;
    private boolean ouvert;

    NiveauInput() {ouvert = false;}

    NiveauInput(String path){
        this.path = path;
    }


    public void setPath(String path){
        this.path = path;
    }

    public void setOuvert(boolean b){
        ouvert = b;
    }

    public String getPath(){
        return path;
    }


    public boolean getOuvert(){ return ouvert;}

    @Override
    public Brick[][] makeNiveau(){
                
        ArrayList<String[]> valList = new ArrayList<String[]>();
        int nbLine = 0;
        //récupération des informations
        try(Scanner scanner = new Scanner(new File(path))){
            while(scanner.hasNextLine() && nbLine < 15){
                String[] courant = scanner.nextLine().split(",");
                if (courant.length != 12) return null;        
                valList.add(courant);    
                nbLine++;
            }        
            if (scanner.hasNextLine()) return null;        
            super.setBrickRows(nbLine);
            
            //création du niveau        
            Brick[][] niv = new Brick[nbLine][12];        
            String[] ligneCourante;
            int intCourant;
            for(int i=0; i < nbLine; i++){
                ligneCourante = valList.get(i);        
                for(int j=0; j < 12; j++){
                    try{
                        intCourant = Integer.parseInt(ligneCourante[j]);
                        switch(intCourant){        
                            case 1: niv[i][j] = new BrickBleue(true, 80, 20); break;
                            case 2: niv[i][j] = new BrickRouge(true, 80, 20); break;
                            case 3: niv[i][j] = new BrickDouble(true, 80, 20); break;
                            case 4: niv[i][j] = new BrickUnbreakable(true, 80, 20); break;
                            default : return null;
                        }
                    }        
                    catch (NumberFormatException a){
                        return null;
                    }
                }
            }        
            return niv;      
        }catch(FileNotFoundException e){
            return null;
        }
    }
    


}
