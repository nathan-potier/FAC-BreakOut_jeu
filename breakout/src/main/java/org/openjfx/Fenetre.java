package org.openjfx;


import java.util.Scanner;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.Component;


public class Fenetre implements ActionListener{
    
    private JTextField inputField;
    private JFrame cadre;
    private JButton bouton;
    private JLabel t1;
    private NiveauInput niv;
    private JPanel p;

    Fenetre(NiveauInput n){
        inputField = new JTextField(16);
        cadre = new JFrame("Niveau personnalisé");
        t1 = new JLabel("Entrez le chemin d'accès");
        bouton = new JButton("Entrer");
        bouton.addActionListener(this);
        niv = n;
        
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
            try (Scanner scanner = new Scanner(new File(path))){
                scanner.close();
                niv.setPath(path);
                niv.setOuvert(true);
                cadre.dispose();
            }
            catch(FileNotFoundException err){
                inputField.setText("");
                t1.setText("Chemin ou fichier invalide");
            }
        }
    }

    public void setVisible(boolean b){
        cadre.setVisible(b);
    }

    public boolean isActive(){
        return cadre.isActive();
    }












}


