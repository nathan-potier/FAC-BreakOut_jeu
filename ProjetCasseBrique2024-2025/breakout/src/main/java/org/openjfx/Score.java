package org.openjfx;

import java.io.*;
import java.util.*;

public class Score {

    // Méthode pour sauvegarder un score pour un pseudo
    public static void saveScoreToFile(String pseudo, int score) {
        String fileName = "scores.txt";
        List<String> lines = getAllScores();  // Récupérer toutes les lignes du fichier
        boolean pseudoFound = false;

        // Parcourir les lignes pour trouver le pseudo
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith(pseudo + ":")) {
                pseudoFound = true;
                String scoreString = line.substring(pseudo.length() + 1);
                List<Integer> scoreList = parseScores(scoreString);
                scoreList.add(score);
                Collections.sort(scoreList);

                // Ne garder que les 5 meilleurs scores
                if (scoreList.size() > 5) {
                    scoreList = scoreList.subList(scoreList.size() - 5, scoreList.size());
                }

                // Mettre à jour la ligne avec les nouveaux scores
                lines.set(i, pseudo + ":" + formatScores(scoreList));   
                break;
            }
        }

        // Si le pseudo n'existe pas, on l'ajoute à la fin
        if (!pseudoFound) {
            lines.add(pseudo + ":" + score);
        }

        // Écriture dans le fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde du score : " + e.getMessage());
        }
    }

    // Méthode pour formater les scores en chaîne
    private static String formatScores(List<Integer> scoreList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < scoreList.size(); i++) {
            sb.append(scoreList.get(i));
            if (i < scoreList.size() - 1) {
                sb.append(","); // Ajoute une virgule entre les scores
            }
        }
        return sb.toString(); // Retourne la chaîne formatée
    }


    // Méthode pour récupérer tous les pseudos
    public static List<String> getAllPseudos() {
        List<String> lines = getAllScores();
        List<String> pseudos = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length > 0) {
                pseudos.add(parts[0]);
            }
        }

        return pseudos;
    }

    // Méthode pour obtenir tous les scores d'un pseudo donné
    public static List<Integer> getScoresForPseudo(String pseudo) {
        List<String> lines = getAllScores();

        for (String line : lines) {
            if (line.startsWith(pseudo + ":")) {
                String scoreString = line.substring(pseudo.length() + 1);
                return parseScores(scoreString);
            }
        }

        System.out.println("Aucun score trouvé pour le pseudo : " + pseudo);
        return new ArrayList<>();
    }

    // Méthode pour obtenir le meilleur score d'un pseudo
    public static int getBestScore(String pseudo) {
        List<Integer> scores = getScoresForPseudo(pseudo);
        if (!scores.isEmpty()) {
            return Collections.max(scores);
        } else {
            return 0;
        }
    }

    // Méthode pour obtenir les 5 derniers scores d'un pseudo
    public static int [] getLastFiveScores(String pseudo) {
        List<Integer> scores = getScoresForPseudo(pseudo);
        

        int [] res = new int[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            res[i] = scores.get(i);
        }
        return res;
    }

    // Méthode auxiliaire pour lire toutes les lignes du fichier
    private static List<String> getAllScores() {
        String fileName = "scores.txt";
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Aucun fichier de scores trouvé.");
        }

        return lines;
    }

    // Méthode auxiliaire pour convertir une chaîne de scores en liste d'entiers
    private static List<Integer> parseScores(String scoreString) {
        List<Integer> scoreList = new ArrayList<>();
        String[] scoreArray = scoreString.split(",");
        for (String s : scoreArray) {
            scoreList.add(Integer.parseInt(s.trim()));
        }
        return scoreList;
    }
}
