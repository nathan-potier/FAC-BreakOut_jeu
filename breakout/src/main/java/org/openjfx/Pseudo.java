package org.openjfx;

public class Pseudo {
    private static String pseudo ;

    // Méthode pour définir le pseudo
    public static void setPseudo(String newPseudo) {
        pseudo = newPseudo;
    }

    // Méthode pour obtenir le pseudo
    public static String getPseudo() {
        return pseudo;
    }

    // Réinitialiser le pseudo
    public static void resetPseudo() {
        pseudo = "";
    }

    public static boolean estDef(){
        return pseudo != null;
    }
}
