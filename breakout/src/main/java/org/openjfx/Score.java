package org.openjfx;

import java.io.*;
import java.util.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Score {

    private static final int BIT_LENGTH = 2048;
    private static BigInteger n, e, d;

    // Initialisation de la clé
    static {
        generateKeys();
    }

    private static void generateKeys() {
        SecureRandom random = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH / 2, random);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH / 2, random);
        n = p.multiply(q);

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537);
        d = e.modInverse(phi);
    }

    public static String encryptScore(String score) {
        BigInteger message = new BigInteger(score.getBytes());
        BigInteger encrypted = message.modPow(e, n);
        return encrypted.toString();
    }

    public static String decryptScore(String encryptedScore) {
        BigInteger encrypted = new BigInteger(encryptedScore);
        BigInteger decrypted = encrypted.modPow(d, n);
        return new String(decrypted.toByteArray());
    }

    public static void saveScoreToFile(String pseudo, int score) {
        String fileName = "scores.txt";
        List<String> lines = getAllScores();
        boolean pseudoFound = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.startsWith(pseudo + ":")) {
                pseudoFound = true;
                String scoreString = line.substring(pseudo.length() + 1);
                String decryptedScores = decryptScore(scoreString); // Déchiffrer les scores
                List<Integer> scoreList = parseScores(decryptedScores);
                scoreList.add(score);
                Collections.sort(scoreList, Collections.reverseOrder());

                if (scoreList.size() > 5) {
                    scoreList = scoreList.subList(0, 5);
                }

                String encryptedScores = encryptScore(formatScores(scoreList)); // Chiffrer les scores
                lines.set(i, pseudo + ":" + encryptedScores);
                break;
            }
        }

        if (!pseudoFound) {
            String encryptedScore = encryptScore(String.valueOf(score));
            lines.add(pseudo + ":" + encryptedScore);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde du score : " + e.getMessage());
        }
    }

    private static String formatScores(List<Integer> scoreList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < scoreList.size(); i++) {
            sb.append(scoreList.get(i));
            if (i < scoreList.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

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

    public static List<Integer> getScoresForPseudo(String pseudo) {
        List<String> lines = getAllScores();

        for (String line : lines) {
            if (line.startsWith(pseudo + ":")) {
                String encryptedScoreString = line.substring(pseudo.length() + 1);
                String decryptedScores = decryptScore(encryptedScoreString); // Déchiffrer les scores
                return parseScores(decryptedScores);
            }
        }
        return new ArrayList<>();
    }

    public static int getBestScore(String pseudo) {
        List<Integer> scores = getScoresForPseudo(pseudo);
        return scores.isEmpty() ? 0 : Collections.max(scores);
    }

    public static int[] getLastFiveScores(String pseudo) {
        List<Integer> scores = getScoresForPseudo(pseudo);
        int size = Math.min(scores.size(), 5);
        int[] lastFiveScores = new int[size];
        for (int i = 0; i < size; i++) {
            lastFiveScores[i] = scores.get(scores.size() - size + i);
        }
        return lastFiveScores;
    }

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

    private static List<Integer> parseScores(String scoreString) {
        List<Integer> scoreList = new ArrayList<>();
        String[] scoreArray = scoreString.split(",");
        
        for (String s : scoreArray) {
            try {
                scoreList.add(Integer.parseInt(s.trim()));
            } catch (NumberFormatException e) {
                
                // pour le signaler ou le supprimer.
                System.out.println("Score invalide détecté et ignoré : " + s);
            }
        }
        
        return scoreList;
    }
    
}
