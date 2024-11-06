package org.openjfx;

import java.io.*;
import java.util.*;
import java.math.BigInteger;


public class Score {

    
    private static BigInteger n, e, d;

    // Initialisation de la clé
    static {
        InitKeys();
    }

    private static void InitKeys() {
        n = new BigInteger("21838405915227455238097712549697641807449329640191825970506920069636643123578658453165521401411383063750841675583473952636427621160706909916578831225167967916595068335657026754028972353680679553951859240262190570642535356627658429297122573414502563090463971217319662499538611188129369540816785157579188418547705786815302228572632566194183866727153992528829419082526652030922897719704301850722488376993285285607645271699750262140236553773634396139878156254532896953580334350084294023573975754622350651238214455471076209664086088725684305845226299910310221064291187812863189795270876219130589443449621963871577052523797");
        e = new BigInteger("65537");
        d = new BigInteger("3500836055134956509017113509118870635351979144603129890689926335529587449170962749423332893529273394689508867413521786874564117794747803463441677233495806505206948562406163282997823878843542111995029268629851444758998374303525938907724945546679950681728099876545468578362644752467875496220778260608922494549019465085408374569304213246324284401560257557144599193146313735009322903200989792934418561248888561067976974051475539021860887558885062496078904941690085977111913865139707223658380889490876330514866604924233997800627682346498533225881780790543706725768696412630889992537248070139988768156077609927483351775041");

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
        int max = 0;
        for (int i = 0; i < scores.size(); i++) {
            if(max < scores.get(i)){
                max = scores.get(i);
            }
        }
        return max;
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
