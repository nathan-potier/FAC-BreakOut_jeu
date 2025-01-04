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

public class TestScore {
    
    private final String fileName = "scores.txt";
    private final String backupFileName = "scores_backup.txt";

    // Avant chaque test, on crée une copie du fichier de scores original
    @BeforeEach
    public void setup() throws IOException {
        File originalFile = new File(fileName);
        File backupFile = new File(backupFileName);

        // Si le fichier original existe, on le copie
        if (originalFile.exists()) {
            copyFile(originalFile, backupFile);
        }


        // Écraser le fichier original en l'ouvrant en mode écriture sans ajouter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))) {
            // Le fichier est réinitialisé avec un contenu vide
            writer.write("");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écrasement du fichier de scores : " + e.getMessage());
        }


    }

    // Après chaque test, on restaure le fichier de scores à partir de la sauvegarde
    @AfterEach
    public void cleanup() throws IOException {
        File originalFile = new File(fileName);
        File backupFile = new File(backupFileName);

        // Si le fichier de sauvegarde existe, on le restaure
        if (backupFile.exists()) {
            copyFile(backupFile, originalFile);
            backupFile.delete(); // Supprime le fichier de sauvegarde après restauration
        } else {
            // Si aucun fichier de sauvegarde n'existe, on supprime le fichier de test
            originalFile.delete();
        }
    }


    private void copyFile(File source, File dest) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(source));
             BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) {
    
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine(); // Assure qu'on écrit chaque ligne correctement
            }
        }
    }

   @Test
    public void testSaveScoreToFile_NewPseudo() {
        Score.saveScoreToFile("Alice", 100);

        List<Integer> scores = Score.getScoresForPseudo("Alice");
        assertEquals(1, scores.size());
        assertEquals(100, scores.get(0));
    }

    @Test
    public void testSaveScoreToFile_ExistingPseudo() {
        Score.saveScoreToFile("Bob", 150);
        Score.saveScoreToFile("Bob", 200);

        List<Integer> scores = Score.getScoresForPseudo("Bob");
        assertEquals(2, scores.size());
        assertEquals(150, scores.get(0));
        assertEquals(200, scores.get(1));
    }

    @Test
    public void testSaveScoreToFile_LimitFiveScores() {
        Score.saveScoreToFile("Charlie", 50);
        Score.saveScoreToFile("Charlie", 60);
        Score.saveScoreToFile("Charlie", 70);
        Score.saveScoreToFile("Charlie", 80);
        Score.saveScoreToFile("Charlie", 90);
        Score.saveScoreToFile("Charlie", 100); // 6ème score

        List<Integer> scores = Score.getScoresForPseudo("Charlie");
        assertEquals(5, scores.size());
        assertFalse(scores.contains(50));
    }

    @Test
    public void testGetAllPseudos() {
        Score.saveScoreToFile("David", 120);
        Score.saveScoreToFile("Emma", 140);

        List<String> pseudos = Score.getAllPseudos();
        assertEquals(2, pseudos.size());
        assertTrue(pseudos.contains("David"));
        assertTrue(pseudos.contains("Emma"));
    }

    @Test
    public void testGetBestScore() {
        Score.saveScoreToFile("Frank", 300);
        Score.saveScoreToFile("Frank", 400);
        Score.saveScoreToFile("Frank", 350);

        int bestScore = Score.getBestScore("Frank");
        assertEquals(400, bestScore);
    }

    @Test
    public void testGetOldBestScoreForMessage(){
        Score.saveScoreToFile("Anne", 100);
        Score.saveScoreToFile("Anne", 150); //ancien meilleur score

        int oldBestScore = Score.getOldBestScoreForMessage("Anne", 200);
        assertEquals(150, oldBestScore);
    }

    @Test
    public void testGetLastFiveScores() {
        Score.saveScoreToFile("Grace", 10);
        Score.saveScoreToFile("Grace", 20);
        Score.saveScoreToFile("Grace", 30);
        Score.saveScoreToFile("Grace", 40);
        Score.saveScoreToFile("Grace", 50);
        Score.saveScoreToFile("Grace", 60); // 6ème score

        int[] lastFiveScores = Score.getLastFiveScores("Grace");
        assertEquals(5, lastFiveScores.length);
        assertArrayEquals(new int[]{20, 30, 40, 50, 60}, lastFiveScores);
    }

    @Test
    public void testFileNotFound() {
        List<Integer> scores = Score.getScoresForPseudo("Unknown");
        assertTrue(scores.isEmpty());
    }

}
