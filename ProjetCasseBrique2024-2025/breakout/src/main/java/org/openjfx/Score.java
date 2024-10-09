package org.openjfx;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class Score {
        // Méthode pour sauvegarder le score dans un fichier local
        public static void saveScoreToFile(int score) {
            String fileName = "scores.txt";
            ArrayList<Integer> scores = getScores();

            Collections.sort(scores);
            
            //garder les 5 derniers
            if (scores.size() > 5) {
                scores = new ArrayList<>(scores.subList(scores.size() - 5, scores.size()));
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                for (int e : scores){
                    writer.write(String.valueOf(e));
                    writer.newLine(); // retour a la ligne

                }
                
            } catch (IOException e) {
                System.out.println("Erreur lors de la sauvegarde du score : " + e.getMessage());
            }

        

        }

        public static ArrayList<Integer> getScores(){
            String fileName = "scores.txt";
            ArrayList<Integer> scores = new ArrayList<>();
            try (Scanner scanner = new Scanner(new File(fileName))) {

                while (scanner.hasNextInt()) {
                    int score = scanner.nextInt();
                    scores.add(score);
                }

            } catch (FileNotFoundException e) {
                System.out.println("Aucun fichier de scores trouvé.");
            }
            return scores;

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

        // Méthode pour obtenir les 5 derniers scores
        public static int[] getLastFiveScores() {
            String fileName = "scores.txt";
            List<Integer> scores = new ArrayList<>();
            try (Scanner scanner = new Scanner(new File(fileName))) {
                while (scanner.hasNextInt()) {
                    scores.add(scanner.nextInt());
                }
            } catch (FileNotFoundException e) {
                System.out.println("Aucun fichier de scores trouvé.");
            }

            // Obtenir les 5 derniers scores
            int[] lastFiveScores = new int[5];
            int startIndex = Math.max(0, scores.size() - 5);
            for (int i = 0; i < 5; i++) {
                lastFiveScores[i] = (startIndex + i < scores.size()) ? scores.get(startIndex + i) : 0; // Remplir avec 0 si moins de 5 scores
            }

            return lastFiveScores;
        }

        
}
