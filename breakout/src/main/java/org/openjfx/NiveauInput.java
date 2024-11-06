package org.openjfx;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

/**
 * La classe NiveauInput hérite de la classe Niveau et sert à générer un niveau en fonction 
 * d'un fichier plutôt que d'une difficulté. Possède un attribut path pour le chemin du fichier
 * et d'un attribut ouvert indiquant si l'objet est toujours solicité.
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul> 
 * <li> Fixe le chemin à path {@link #setPath(String)}.</li>
 * <li> Fixe l'ouverture à b {@link #setOuvert(boolean)}.</li>
 * <li> Renvoie le chemin stocké {@link #getPath()}.</li>
 * <li> Renvoie l'ouverture de l'objet {@link #getOuvert()}.</li>
 * <li> Crée un tableau de brique à partir du fichier décrit par path {@link #makeNiveau()}.</li>
 * </ul>
 * 
 * @author Le-Augustin NGUYEN
 * @version 1.0
 * 
 */

public class NiveauInput extends Niveau{

    private String path;
    private boolean ouvert;

    NiveauInput() {ouvert = false;}


    /**
     * Fixe le chemin à path.
     * 
     * @param path String correspondant à un emplacement de fichier.
     */
    public void setPath(String path){
        this.path = path;
    }

    /**
     * Fixe si l'objet est en train d'être utilisé ou non.
     * 
     * @param b booléen déterminant l'utilisation de l'objet.
     */
    public void setOuvert(boolean b){
        ouvert = b;
    }

    /**
     * Renvoie le chemin du fichier
     * 
     * @return String représentant un fichier stocké à path;
     */
    public String getPath(){
        return path;
    }

    /**
     * Renvoie si l'objet est utilisé ou non.
     * 
     * @return boolean représentant l'état de l'objet.
     */
    public boolean getOuvert(){ return ouvert;}


    /**
     * Crée un niveau à partir du fichier stocké dans path.
     * 
     * @return tableau à deux dimensions de Brick interprété avec le contenu du fichier à path, si invalide renvoie null
     */
    @Override
    public Brick[][] makeNiveau(){
                
        ArrayList<String[]> valList = new ArrayList<String[]>();
        int nbLine = 0;
        String[] courant;

        try(Scanner scanner = new Scanner(new File(path))){
            if (!scanner.hasNextLine()) return null;
            while(scanner.hasNextLine() && nbLine < 14){
                courant = scanner.nextLine().split(",");
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
                            case 1: niv[i][j] = new BrickBleue(true, 80, 20,PowerUp.randomPU(j * 85, i * 25)); break;
                            case 2: niv[i][j] = new BrickDouble(true, 80, 20,PowerUp.randomPU(j * 85, i * 25)); break;
                            case 3: niv[i][j] = new BrickRouge(true, 80, 20, PowerUp.randomPU(j * 85, i * 25)); break;
                            case 4: niv[i][j] = new BrickUnbreakable(true, 80, 20, PowerUp.randomPU(j * 85, i * 25)); brickDetruite(); break;
                            case 5: niv[i][j] = new BrickBleue(false, 80, 20, PowerUp.randomPU(j * 85, i * 25)); brickDetruite(); break;
                            default : return null;
                        }
                        brickAdd();
                    }        
                    catch (NumberFormatException a){
                        return null;
                    }
                }
            }        
            setInitNbBrick();
            setTabBrick(niv);
            return niv;      
        }catch(FileNotFoundException e){
            return null;
        }
    }
    


}
