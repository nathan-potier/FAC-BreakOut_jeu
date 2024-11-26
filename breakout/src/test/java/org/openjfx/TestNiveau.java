package org.openjfx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.List;
import java.util.Random;

public class TestNiveau{

    private Niveau niv;
    private int diff;
    private Brick[][] tab;

    public void printTab(Brick[][] tab){
        for(Brick[] i : tab){
            for(Brick j : i){
                System.out.print(((j!= null)? 1: 0) + ", ");
            }
            System.out.println();
        }
    }


    // Test si la valeur est bien mise à 1 si diff <1
    @Test
    public void testNegRow(){
        niv = new Niveau(-5312);
        assertEquals(niv.getBrickRows(), 5);
    }

    // Test si la valeur est bien mise à 1 sur la valeur critique en 0
    @Test
    public void testNegRowCrit(){
        niv = new Niveau(0);
        assertEquals(niv.getBrickRows(), 5);
    }

    // Test un cas de base
    @Test
    public void testRowBase(){
        niv = new Niveau(8);
        assertEquals(niv.getBrickRows(), 12);
    }

    // Test si la valeur est bien mise à 10 si diff > 10
    @Test
    public void testRowSupp(){
        niv = new Niveau(456);
        assertEquals(niv.getBrickRows(), 12);
    }

    // Test si la valeur est bien mise à 1 sur la valeur critique en 11
    @Test
    public void testRowSuppCrit(){
        niv = new Niveau(11);
        assertEquals(niv.getBrickRows(), 12);
    }


    // Test la taille du tableau pour une valeur négative
    @Test
    public void testTailleNeg(){
        niv = new Niveau(-5312);
        tab = niv.makeNiveau();
        assertEquals(tab.length, 5);
    }


    // Test la taille du tableau pour une valeur de base
    @Test
    public void testTailleBase(){
        niv = new Niveau(6);
        tab = niv.makeNiveau();
        assertEquals(tab.length, 10);
    }


    // Test la taille du tableau pour une valeur de supérieur
    @Test
    public void testTailleSupp(){
        niv = new Niveau(1526);
        tab = niv.makeNiveau();
        assertEquals(tab.length, 12);
    }


    // Test si le tableau est bien rempli avec en entrée un valeur négatif
    @Test
    public void testNegTab(){
        niv = new Niveau(-5312);
        tab = niv.makeNiveau();
        boolean flag = true;
        for(int i=0; i< 5; i++){
            for(Brick b : tab[i]){
                flag &= (b != null); 
            }
        }
        assertTrue(flag);
    }

    // Test si le tableau est bien rempli avec en entrée un valeur attendu
    @Test
    public void testBaseTab(){
        niv = new Niveau(5);
        tab = niv.makeNiveau();
        boolean flag = true;
        for(int i=0; i < 9; i++){
            for(Brick b : tab[i]){
                flag &= (b != null); 
            }
        }
        assertTrue(flag);
    }


    // Test si le tableau est bien rempli avec en entrée un valeur supérieur
    @Test
    public void testSuppTab(){
        niv = new Niveau(451);
        tab = niv.makeNiveau();
        boolean flag = true;
        for(int i=0; i< 12; i++){
            for(Brick b : tab[i]){
                flag &= (b != null); 
            }
        }
        assertTrue(flag);
    }






}