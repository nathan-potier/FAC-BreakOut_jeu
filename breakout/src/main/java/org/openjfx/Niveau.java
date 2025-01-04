package org.openjfx;


import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



/**
 * La classe Niveau sert à générer des tableaux à deux dimensions de briques 
 * de façon aléatoire en fonction de la difficulté et garde un compteur du nombre de brique
 * cassable initial et effectif.
 * 
 * 
 * <p>
 * Les méthodes présentes:
 * </p>
 * 
 * <ul>
 * <li> Fixe à un entier n le nombre de ligne {@link #setBrickRows(int)}.</li>
 * <li> Incrémente de 1 le nombre de brique non-cassé {@link #brickAdd()}.</li>
 * <li> Décrémente de 1 le nombre de brique non-cassé {@link #brickDetruite()}.</li>
 * <li> Renvoie si toutes les briques ont été cassé {@link #finDeJeu()}.</li>
 * <li> Fait considerer toutes les briques comme cassées (fonction pour aider aux tests) {@link #setBrickToBreak()}.</li>
 * <li> Fixe le nombre total de brique cassable du niveau {@link #setInitNbBrick()}.</li>
 * <li> Réinitialise l'état des briques {@link #resetBrick()}.</li>
 * <li> Crée un tableau à deux dimensions de briques selon la difficulté {@link #makeNiveau()}.</li>
 * <li> Crée un niveau à partir du fichier stocké dans filePath {@link #makeNiveauExperimental(String)}</li>
 * </ul>
 * 
 * @author Le-Augustin NGUYEN
 * @version 1.0
 * 
 */


public class Niveau {

    private int brickRows;
    private int brickCols = 12;
    private int diff; /* diff >=1 */
    private int nbBrickLeft = 0;
    private int initNbBrick;
    private Brick[][] tabBrick;


    /**
     * Constructeur d'un objet Niveau à partir d'une difficulté ajusté si la valeur est incompatible.
     * Défini également le nombre de lignes du tableau.
     *  
     * @param diff un entier représentant la difficulté
    */
    Niveau(int diff){
        this.diff = (diff < 1)? 1 : Math.min(diff, 8);
        brickRows = Math.min(5 + this.diff - 1, 12);

    }

    /**
     * Constructeur d'un objet Niveau pour des niveaux personnalisé, avec une difficulté mise à 0.
    */
    Niveau(){
        diff = 0;
    }

    public void defBrick(){
        tabBrick = new Brick[14][12];
    }


    /**
     * Renvoie le tableau représentatif du niveau
     * 
     * @return tableau à deux dimensions de brick
     */
    public Brick[][] getAll(){
        return tabBrick;
    }


    /**
     * Renvoie l'élément à la place x,y.
     * 
     * @param x entier représentant la x-ème ligne du tableau.
     * @param y entier représentant la y-ème colonne du tableau.
     * @return Brick à l'emplacement [x][y] du tableau.
     */
    public Brick getBrick(int x, int y){
        return tabBrick[x][y];
    }

    /**
     * Renvoie le nombre de ligne du tableau.
     * 
     * @return Entier correspondant au nombre de ligne du tableau.
     */
    public int getBrickRows(){
        return brickRows;
    }

    /**
     * 
     * @param x entier représentant la x-ème ligne du tableau.
     * @param y entier représentant la y-ème colonne du tableau.
     * @param b Brick remplaçant l'ancienne brick en x,y.
     */
    public void setBrick(int x, int y, Brick b){
        if (tabBrick != null) tabBrick[x][y] = b;
    }

    /**
     * Fixe le nombre de ligne à n, destinée au niveau personnalisé.
     * 
     * @param n Entier représentant le nombre de ligne du tableau à creer.
     */
    public void setBrickRows(int n){
        brickRows = n;
    }

    /**
     * Incrémentre le compteur de brique non-cassé.
     */
    public void brickAdd(){
        nbBrickLeft++;
    }

    /**
     * Décrémente le compteur de brique non-cassé.
     */
    public void brickDetruite(){
        nbBrickLeft--;
    }

    /**
     * Retourne s'il ne reste plus de brique à casser.
     * 
     * @return booléen correspondant à si le nombre de brique non-cassé restant est nul.
     */
    public boolean finDeJeu(){
        return nbBrickLeft == 0;
    }
    
    /**
     * Considère toutes les briques comme cassées.
     */
    public void setBrickToBreak(){
        nbBrickLeft = 0;
    }

    /**
     * Initialise le nombre de briques total du tableau, invoqué dans {@link #makeNiveau()}.
     */
    public void setInitNbBrick(){
        initNbBrick = nbBrickLeft;
    }

    /**
     * Reconsidère toutes les briques comme à leur état initial.
     */
    public void resetBrick(){
        nbBrickLeft = initNbBrick;
    }

    /**
     * fixe le tableau représentant le niveau au tableau entré.
     * 
     * @param tab tableau à deux dimensions de briques.
     */
    public void setTabBrick(Brick[][] tab){
        tabBrick = tab;
    }


    /**
     * affiche toutes les briques encore non cassé.
     * 
     * @param gc fenetre graphique
     */
    public void afficheNiveau(GraphicsContext gc){
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickCols; j++) {
                if (tabBrick[i][j].isNotBreak) {
                    double brickX = j * (tabBrick[i][j].getWidth() + 5);
                    double brickY = i * (tabBrick[i][j].getHeight() + 5);
                    double imageWidth = tabBrick[i][j].getWidth();
                    double imageHeight = tabBrick[i][j].getHeight();
                    
                    if (tabBrick[i][j] instanceof BrickBleue) {
                        BrickBleue brickBleue = (BrickBleue) tabBrick[i][j];
                        gc.drawImage(brickBleue.imageBleue, brickX, brickY + 20, imageWidth, imageHeight);
                    }else if (tabBrick[i][j] instanceof BrickDouble) {
                        BrickDouble brickDouble = (BrickDouble) tabBrick[i][j];
                        gc.drawImage(brickDouble.imageAct, brickX, brickY + 20, imageWidth, imageHeight);
                    }else if (tabBrick[i][j] instanceof BrickRouge) {
                        BrickRouge brickRouge = (BrickRouge) tabBrick[i][j];
                        gc.drawImage(brickRouge.imageRouge, brickX, brickY + 20, imageWidth, imageHeight);
                    }else if (tabBrick[i][j] instanceof BrickUnbreakable) {
                        BrickUnbreakable brickUnbreakable = (BrickUnbreakable) tabBrick[i][j];
                        gc.drawImage(brickUnbreakable.imageUnbreakable, brickX, brickY + 20, imageWidth, imageHeight);
                    } else {
                        // Affichage de la couleur de la brique normale
                        gc.setFill(tabBrick[i][j].couleur());
                        gc.fillRect(brickX, brickY + 20, tabBrick[i][j].getWidth(), tabBrick[i][j].getHeight());
                    }
                }
            }
        }
    }










    /**
     * Renvoie un tableau de briques généré en fonction de la difficulté.
     * 
     * @return tableau bi-dimensionnel d'objet Brick.
     */
    public Brick[][] makeNiveau(){
        Random rd = new Random();
        Brick[][] tab = new Brick[brickRows][brickCols];
        
        // nb de ligne de brique incrémenté de 1 par niveau de difficulté -1 
        for(int i = 0; i < brickRows; i++){
            for (int j = 0; j < brickCols; j++){
                int val = rd.nextInt(100);

                // probabilité d'avoir une brique incassable (augmente selon la diff)
                if (val < diff*2){
                    tab[i][j] = new BrickUnbreakable(true, 80, 20, PowerUp.randomPU(j * 85,i * 25 ));
                    
                
                    // probabilité d'avoir une brique avec bonus  (diminue selon la diff)
                }else 
                nbBrickLeft++;
                if (val < 20){
                    tab[i][j] = new BrickRouge(true, 80, 20, PowerUp.randomPU(j * 85,i * 25 )); 
                    

                    //probabilité d'avoir une brique a 2 pv (augmente selon la diff)
                }else if (val < 20 + (5 * diff)) {
                    tab[i][j] = new BrickDouble(true, 80, 20,PowerUp.randomPU(j * 85,i * 25 ));
                    

                }else{
                    tab[i][j] = new BrickBleue(true, 80, 20, PowerUp.randomPU(j * 85,i * 25 ));
                  
                }

            }
        }
        setInitNbBrick();
        setTabBrick(tab);
        return tab;
    }

     /**
     * Crée un niveau à partir du fichier stocké dans filePath
     * 
     * @param filePath Le chemin d'accès au fichier contenant la configuration du niveau où chaque ligne correspond à une ligne de briques et chaque valeur à un type de brique.
     * 
     * @return tableau à deux dimensions de Brick représentant le niveau, interprété à partir du contenu du fichier filePath, renvoie null si invalide.
     * 
     */
    public Brick[][] makeNiveauExperimental(String filePath){
        ArrayList<String[]> niveauValues = new ArrayList<String[]>();
        int nbLine = 0;
        String[] courant;

        try(Scanner scanner = new Scanner(new File(filePath))){
            if (!scanner.hasNextLine()) return null;
            while(scanner.hasNextLine() && nbLine < 12){
                courant = scanner.nextLine().split(",");
                if (courant.length != 12) return null;        
                niveauValues.add(courant);    
                nbLine++;
            }        
            if (scanner.hasNextLine()) return null;        
            setBrickRows(nbLine);
            
            //Création du niveau        
            Brick[][] niv = new Brick[nbLine][12];        
            String[] ligneCourante;
            int intCourant;
            for(int i=0; i < nbLine; i++){
                ligneCourante = niveauValues.get(i);        
                for(int j=0; j < 12; j++){
                    try{
                        intCourant = Integer.parseInt(ligneCourante[j]);
                        switch(intCourant){        
                            case 1: niv[i][j] = new BrickBleue(true, 80, 20,PowerUp.randomPU(j * 85, i * 25)); 
                                    diff++; break;
                            case 2: niv[i][j] = new BrickDouble(true, 80, 20,PowerUp.randomPU(j * 85, i * 25)); 
                                    diff++; break;
                            case 3: niv[i][j] = new BrickRouge(true, 80, 20, PowerUp.randomPU(j * 85, i * 25)); 
                                    diff++; break;
                            case 4: niv[i][j] = new BrickUnbreakable(true, 80, 20, PowerUp.randomPU(j * 85, i * 25)); brickDetruite(); 
                                    break;
                            case 5: niv[i][j] = new BrickVide(false, 80, 20, PowerUp.randomPU(j * 85, i * 25)); brickDetruite(); 
                                    break;
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