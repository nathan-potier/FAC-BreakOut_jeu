package org.openjfx;

import java.io.*;
import java.util.*;

/**
 * La classe Score permet de gérer les scores des joueurs et de les sauvegarder
 * dans un fichier. Chaque pseudo de joueur est associé à une liste de scores,
 * et les 5 meilleurs scores sont conservés.
 * 
 * <p>
 * Cette classe offre des méthodes pour :
 * </p>
 * 
 * <ul>
 * <li>Sauvegarder un score pour un joueur avec {@link #saveScoreToFile(String, int)}.</li>
 * <li>Récupérer tous les pseudos enregistrés avec {@link #getAllPseudos()}.</li>
 * <li>Obtenir les scores associés à un pseudo avec {@link #getScoresForPseudo(String)}.</li>
 * <li>Obtenir le meilleur score d'un joueur avec {@link #getBestScore(String)}.</li>
 * <li>Obtenir l'ancien meilleur score d'un joueur avec {@link #getOldBestScoreForMessage(String, int)}.</li>
 * <li>Obtenir les cinq derniers scores d'un joueur avec {@link #getLastFiveScores(String)}.</li>
 * </ul>
 * 
 * <p>
 * Le fichier de score est nommé <strong>scores.txt</strong>, et chaque ligne est
 * structurée sous la forme <code>pseudo:score1,score2,...</code>.
 * </p>
 * 
 * <p>
 * Les méthodes auxiliaires permettent également de lire et d'écrire dans le fichier,
 * et de convertir une chaîne de caractères représentant une liste de scores en une
 * liste d'entiers.
 * </p>
 * 
 * @author Nathan POTIER
 * @version 1.0
 */
public class Score {


    /**
     * Sauvegarde un score pour un joueur donné dans le fichier de scores. Si le pseudo
     * existe déjà, le score est ajouté à sa liste et seuls les 5 meilleurs scores sont
     * conservés.
     *
     * @param pseudo le pseudo du joueur
     * @param score le score à ajouter pour ce joueur
     */
    // Méthode pour sauvegarder un score pour un pseudo
    public static void saveScoreToFile(String pseudo, int score) {
            String fileName = "scores.txt";
        List<String> lines = getAllScores(); // Récupérer toutes les lignes du fichier
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

    /**
     * Formate une liste de scores en une chaîne de caractères, séparée par des virgules.
     * 
     * @param scoreList la liste de scores à formater
     * @return une chaîne de caractères représentant les scores
     */
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

    /**
     * Lit toutes les lignes du fichier de scores et les retourne sous forme de liste.
     * 
     * @return une liste de chaînes représentant chaque ligne du fichier de scores
     */
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

    /**
     * Récupère tous les scores associés à un pseudo donné.
     * 
     * @param pseudo le pseudo du joueur dont on veut les scores
     * @return une liste des scores du joueur, ou une liste vide si le pseudo n'existe pas
     */
    // Méthode pour obtenir tous les scores d'un pseudo donné
    public static List<Integer> getScoresForPseudo(String pseudo) {
        List<String> lines = getAllScores();

        for (String line : lines) {
            if (line.startsWith(pseudo + ":")) {
                String scoreString = line.substring(pseudo.length() + 1);
                return parseScores(scoreString);
            }
        }
        return new ArrayList<>();
    }

    /**
     * Récupère le meilleur score d'un pseudo donné.
     * 
     * @param pseudo le pseudo du joueur
     * @return le meilleur score du joueur, ou 0 si le pseudo n'a aucun score
     */
    // Méthode pour obtenir le meilleur score d'un pseudo
    public static int getBestScore(String pseudo) {
        List<Integer> scores = getScoresForPseudo(pseudo);
        if (!scores.isEmpty()) {
            return Collections.max(scores);
        } else {
            return 0;
        }
    }

    /**
     * Récupère l'ancien meilleur score d'un pseudo donné.
     * 
     * @param pseudo le pseudo du joueur
     * @return l'ancien meilleur score du joueur
     */
    //Méthode pour obtenir l'ancien meilleur score du joueur afin de permettre d'afficher le bon message à la fin de jeu.
    public static int getOldBestScoreForMessage(String pseudo, int nouveauScore){
        List<Integer> scores = getScoresForPseudo(pseudo);
        if(scores.isEmpty()){
            return 0;
        }
        scores.remove(Integer.valueOf(nouveauScore)); //Supprime temporairement le nouveau score si présent dans la liste
        if(scores.isEmpty()){
            return nouveauScore;
        }
        return Collections.max(scores);
    }

    /**
     * Récupère les cinq derniers scores d'un pseudo donné.
     * 
     * @param pseudo le pseudo du joueur
     * @return un tableau des cinq derniers scores du joueur
     */
    // Méthode pour obtenir les 5 derniers scores d'un pseudo
    public static int[] getLastFiveScores(String pseudo) {
        List<Integer> scores = getScoresForPseudo(pseudo);

        int[] res = new int[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            res[i] = scores.get(i);
                    }
        return res;
    }

    /**
     * Lit toutes les lignes du fichier de scores et les retourne sous forme de liste.
     * 
     * @return une liste de chaînes représentant chaque ligne du fichier de scores
     */
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

    /**
     * Convertit une chaîne de scores en une liste d'entiers.
     * 
     * @param scoreString la chaîne de scores à convertir
     * @return une liste d'entiers représentant les scores
     */
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
