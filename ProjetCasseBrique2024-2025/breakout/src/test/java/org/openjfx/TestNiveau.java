package org.openjfx;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.List;
import java.util.Random;

public class TestNiveau{

    private Niveau niv;
    private int diff;
    private Brick[][] tab;


    public void def(int diff){
        Niveau niv = new Niveau(diff); 
        tab = niv.makeNiveau();
    }

    // Test si la valeur est bien mise à 1 si diff <1
    @Test
    public void testNegDiff(){
        def(-53);
        assertEquals(niv.getDiff(), 1);
    }

    // Test si la valeur est bien mise à 1 sur la valeur critique en 0
    @Test
    public void testNegDiffCrit(){
        def(0);
        assertEquals(niv.getDiff(), 1);
    }

    // Test un cas de base
    @Test
    public void testDiffBase(){
        def(8);
        assertEquals(niv.getDiff(), 8);
    }

    // Test si la valeur est bien mise à 10 si diff >10
    @Test
    public void testDiffSupp(){
        def(456);
        assertEquals(niv.getDiff(), 10);
    }

    // Test si la valeur est bien mise à 1 sur la valeur critique en 11
    @Test
    public void testDiffSuppCrit(){
        def(11);
        assertEquals(niv.getDiff(), 10);
    }



    // Test si la valeur est bien mise à 1 si diff <1
    @Test
    public void testNegRow(){
        def(-53);
        assertEquals(niv.getBrickRows(), 5);
    }

    // Test si la valeur est bien mise à 1 sur la valeur critique en 0
    @Test
    public void testNegRowCrit(){
        def(0);
        assertEquals(niv.getBrickRows(), 5);
    }

    // Test un cas de base
    @Test
    public void testRowBase(){
        def(8);
        assertEquals(niv.getBrickRows(), 12);
    }

    // Test si la valeur est bien mise à 10 si diff >10
    @Test
    public void testRowSupp(){
        def(456);
        assertEquals(niv.getBrickRows(), 10);
    }

    // Test si la valeur est bien mise à 1 sur la valeur critique en 12
    @Test
    public void testRowSuppCrit(){
        def(11);
        assertEquals(niv.getBrickRows(), 14);
    }


    // Test la taille du tableau pour une valeur négative
    @Test
    public void testTailleNeg(){
        def(-5321);
        assertEquals(tab.length, 5);
    }


    // Test la taille du tableau pour une valeur de base
    @Test
    public void testTailleBase(){
        def(6);
        assertEquals(tab.length, 10);
    }


    // Test la taille du tableau pour une valeur de supérieur
    @Test
    public void testTailleSupp(){
        def(1561);
        assertEquals(tab.length, 14);
    }


    // Test si le tableau est bien rempli avec en entrée un valeur négatif
    @Test
    public void testNegTab(){
        def(-6552);
        flag = true;
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
        def(5);
        flag = true;
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
        def(4651);
        flag = true;
        for(int i=0; i< 14; i++){
            for(Brick b : tab[i]){
                flag &= (b != null); 
            }
        }
        assertTrue(flag);
    }






}