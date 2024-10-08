package org.openjfx;
import java.io.*;
import java.util.Scanner;

public class Score {
        // Méthode pour sauvegarder le score dans un fichier local
        public static void saveScoreToFile(int score) {
            String fileName = "scores.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(String.valueOf(score));
                writer.newLine();
            } catch (IOException e) {
                System.out.println("Erreur lors de la sauvegarde du score : " + e.getMessage());
            }
        }
    
        // Méthode pour obtenir le meilleur score à partir du fichier local
        public static int getBestScore() {
            String fileName = "scores.txt";
            int bestScore = 0;
            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNextInt()) {
                    int score = scanner.nextInt();
                    if (score > bestScore) {
                        bestScore = score;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Aucun fichier de scores trouvé.");
            }
            return bestScore;
        }
        
}
