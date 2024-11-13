package org.openjfx;


import java.util.Scanner;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.Component;

public class FenetreEditeur implements ActionListener{
    
    private JTextField inputField;
    private JFrame cadre;
    private JButton bouton;
    private JLabel t1;
    private NiveauInput niv;
    private JPanel p;
    private NiveauEditeur gen;

    FenetreEditeur(){
        inputField = new JTextField(16);
        cadre = new JFrame("Niveau personnalisé");
        t1 = new JLabel("Entrez le nom du fichier");
        bouton = new JButton("Entrer");
        bouton.addActionListener(this);

        
        p = new JPanel();

        p.add(inputField);
        p.add(bouton);
        p.add(t1);

        cadre.add(p);

        cadre.setSize(300, 150);
        cadre.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    // Action quand Entrer est cliqué
    public void actionPerformed(ActionEvent e){

        String action = e.getActionCommand();
        if (action.equals("Entrer")) {

            String path = inputField.getText();
            if ((new File(path)).exists()){
                t1.setText("Un fichier existe déjà à cette emplacement");
                inputField.setText("");
                
            }
            else{
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))){
                    String s;
                    for(int i = 0; i < 14; i++){
                        s = "";
                        for(int j = 0; j < 12; j++ ){
                            if (gen.getBrick(i,j) instanceof BrickBleue && gen.getBrick(i,j).isNotBreak){
                                s += 1;
                            } 
                            else if (gen.getBrick(i,j) instanceof BrickDouble){
                                s += 2;
                            }
                            else if (gen.getBrick(i,j) instanceof BrickRouge){
                                s += 3;
                            }
                            else if (gen.getBrick(i,j) instanceof BrickUnbreakable){
                                s += 4;
                            }
                            else if (gen.getBrick(i,j) instanceof BrickVide){
                                s += 5;
                            }
                            if (j!= 11) s += ",";
                        }
                        writer.write(s + "\n");
                    }

                }catch(IOException o){}
                gen.setOuvert(false);
                cadre.dispose();

            }
        }
    }

    public void setVisible(boolean b){
        cadre.setVisible(b);
    }

    public boolean isActive(){
        return cadre.isActive();
    }

    public void getNiv(NiveauEditeur niv){
        gen = niv;
    }


}
