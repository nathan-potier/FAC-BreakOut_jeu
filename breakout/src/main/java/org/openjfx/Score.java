package org.openjfx;

import java.io.*;
import java.util.*;
import java.util.Base64;

/**
 * Classe Score mise à jour avec chiffrement et déchiffrement des scores.
 */
public class Score {

    private static final String fileName = "scores.txt";

    /**
     * Clé simple pour le chiffrement/déchiffrement (à améliorer pour une vraie sécurité).
     */
    private static final String ENCRYPTION_KEY = "simplekey";

    /**
     * Sauvegarde un score pour un joueur donné dans le fichier de scores. Si le pseudo
     * existe déjà, le score est ajouté à sa liste et seuls les 5 meilleurs scores sont
     * conservés.
     *
     * @param pseudo le pseudo du joueur
     * @param score le score à ajouter pour ce joueur
     */
    public static void saveScoreToFile(String pseudo, int score) {
        List<String> lines = getAllScores();
        boolean pseudoFound = false;

        // Parcourir les lignes pour trouver le pseudo
        for (int i = 0; i < lines.size(); i++) {
            String line = decrypt(lines.get(i)); // Déchiffrer la ligne
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
                lines.set(i, encrypt(pseudo + ":" + formatScores(scoreList)));
                break;
            }
        }

        // Si le pseudo n'existe pas, on l'ajoute à la fin
        if (!pseudoFound) {
            lines.add(encrypt(pseudo + ":" + score));
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

    public static List<String> getAllPseudos() {
        List<String> lines = getAllScores();
        List<String> pseudos = new ArrayList<>();

        for (String line : lines) {
            String decryptedLine = decrypt(line); // Déchiffrer la ligne
            String[] parts = decryptedLine.split(":");
            if (parts.length > 0) {
                pseudos.add(parts[0]);
            }
        }

        return pseudos;
    }

    public static List<Integer> getScoresForPseudo(String pseudo) {
        List<String> lines = getAllScores();

        for (String line : lines) {
            String decryptedLine = decrypt(line); // Déchiffrer la ligne
            if (decryptedLine.startsWith(pseudo + ":")) {
                String scoreString = decryptedLine.substring(pseudo.length() + 1);
                return parseScores(scoreString);
            }
        }
        return new ArrayList<>();
    }

    public static int getBestScore(String pseudo) {
        List<Integer> scores = getScoresForPseudo(pseudo);
        if (!scores.isEmpty()) {
            return Collections.max(scores);
        } else {
            return 0;
        }
    }

    public static int getOldBestScoreForMessage(String pseudo, int nouveauScore) {
        List<Integer> scores = getScoresForPseudo(pseudo);
        if (scores.isEmpty()) {
            return 0;
        }
        scores.remove(Integer.valueOf(nouveauScore)); // Supprime temporairement le nouveau score si présent dans la liste
        if (scores.isEmpty()) {
            return nouveauScore;
        }
        return Collections.max(scores);
    }

    public static int[] getLastFiveScores(String pseudo) {
        List<Integer> scores = getScoresForPseudo(pseudo);

        int[] res = new int[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            res[i] = scores.get(i);
        }
        return res;
    }

    private static List<String> getAllScores() {
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

    private static List<Integer> parseScores(String scoreString) {
        List<Integer> scoreList = new ArrayList<>();
        String[] scoreArray = scoreString.split(",");
        for (String s : scoreArray) {
            scoreList.add(Integer.parseInt(s.trim()));
        }
        return scoreList;
    }

    private static String formatScores(List<Integer> scoreList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < scoreList.size(); i++) {
            sb.append(scoreList.get(i));
            if (i < scoreList.size() - 1) {
                sb.append(","); // Ajoute une virgule entre les scores
            }
        }
        return sb.toString();
    }

    /**
     * Chiffre une chaîne de caractères.
     * 
     * @param data la chaîne à chiffrer
     * @return la chaîne chiffrée
     */
    private static String encrypt(String data) {
        try {
            byte[] encryptedBytes = Base64.getEncoder().encode(data.getBytes());
            return new String(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chiffrement.", e);
        }
    }

    /**
     * Déchiffre une chaîne de caractères.
     * 
     * @param data la chaîne chiffrée
     * @return la chaîne déchiffrée
     */
    private static String decrypt(String data) {
        try {
            byte[] decryptedBytes = Base64.getDecoder().decode(data.getBytes());
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du déchiffrement.", e);
        }
    }
}
