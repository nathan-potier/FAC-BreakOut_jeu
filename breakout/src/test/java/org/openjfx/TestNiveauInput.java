package org.openjfx;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;


public class TestNiveauInput {

    NiveauInput niv;
    Brick[][] tab;
    


    // Test avec un fichier du bon format
    // Test du nombre de ligne du tableau obtenu
    @Test
    public void testInputBaseLigne() throws IOException{
        File testFile = new File("fichierTest.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {


            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
        } catch (IOException e) {
        }

        boolean flag = true;
        niv = new NiveauInput();
        niv.setPath("fichierTest.txt");
        tab = niv.makeNiveau();

        assertEquals(tab.length, 1);
        testFile.delete();

    }



    // Test avec un fichier du bon format
    // Test du nombre de colonne du tableau obtenu
    @Test
    public void testInputBaseColone() throws IOException{
        File testFile = new File("fichierTest.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {

            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
        } catch (IOException e) {
        }

        boolean flag = true;
        niv = new NiveauInput();
        niv.setPath("fichierTest.txt");
        tab = niv.makeNiveau();

        assertEquals(tab[0].length, 12);
        testFile.delete();

    }


    // Test avec un fichier vide
    @Test
    public void testInputVide() throws IOException{
        File testFile = new File("fichierTest.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {


            writer.write("");
        } catch (IOException e) {
        }

        boolean flag = true;
        niv = new NiveauInput();
        niv.setPath("fichierTest.txt");
        tab = niv.makeNiveau();

        assertEquals(tab, null);
        testFile.delete();

    }


    // Test avec un fichier avec trop de ligne
    @Test
    public void testInputSupLigne() throws IOException{
        File testFile = new File("fichierTest.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {


            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1");
        } catch (IOException e) {
        }

        niv = new NiveauInput();
        niv.setPath("fichierTest.txt");
        tab = niv.makeNiveau();

        assertEquals(tab, null);
        testFile.delete();

    }

    // Test avec un fichier avec trop de colonne
    @Test
    public void testInputSupColonne() throws IOException{
        File testFile = new File("fichierTest.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {


            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
  
        } catch (IOException e) {
        }

        niv = new NiveauInput();
        niv.setPath("fichierTest.txt");
        tab = niv.makeNiveau();

        assertEquals(tab, null);
        testFile.delete();

    }

    // Test avec un fichier avec trop de ligne et trop de colonne
    @Test
    public void testInputSupLigneCol() throws IOException{
        File testFile = new File("fichierTest.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {


            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");
            writer.write("1,1,1,1,1,1,1,1,1,1,1,1,1");

        } catch (IOException e) {
        }

        niv = new NiveauInput();
        niv.setPath("fichierTest.txt");
        tab = niv.makeNiveau();

        assertEquals(tab, null);
        testFile.delete();

    }

    // Test avec un fichier avec des valeurs erronée
    @Test
    public void testInputVal() throws IOException{
        File testFile = new File("fichierTest.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testFile))) {


            writer.write("1,1,1,9,1,3,2,8,1,1,1,1" + "\n");
            writer.write("1,54,1,1,1,7,1,1,1,1,1,1");
  
        } catch (IOException e) {
        }

        boolean flag = true;
        niv = new NiveauInput();
        niv.setPath("fichierTest.txt");
        


        assertEquals(tab, null);
        testFile.delete();

    }

 


    
}
