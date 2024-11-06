package org.openjfx;

import java.math.BigInteger;

public class Test {

   private static BigInteger n, e, d;

    // Initialisation de la clé
    static {
        InitKeys();
    }

    private static void InitKeys() {
        n = new BigInteger("70555910811427831932482023648965701514152574");  // Un petit nombre pour n67*7
        e = new BigInteger("65537");    // Clé publique (souvent choisie comme un petit nombre premier)
        d = new BigInteger("31691324695301907145520468536136199219656137");  // Clé privée correspondante
    }

    public static String encryptScore(String score) {
        BigInteger message = new BigInteger(score.getBytes());
        System.out.println(message);
        BigInteger encrypted = message.modPow(e, n);
        return encrypted.toString();
    }

    public static String decryptScore(String encryptedScore) {
        BigInteger encrypted = new BigInteger(encryptedScore);
        BigInteger decrypted = encrypted.modPow(d, n);
        return new String(decrypted.toByteArray());
    }

    public static String convertToAscii(String input) {
        String res = "";
       
        for (int i = 0; i < input.length(); i++){
            res += (int)input.charAt(i);

        }
        return res;
    }


    public static void main(String[] args) {
        String original = "1";

        System.out.println(convertToAscii(original).length());
        String encrypted = encryptScore(original);
        String decrypted = decryptScore(encrypted); 

        System.out.println("Original: " + original);
        System.out.println("Encrypté : " + encrypted);
        System.out.println("Décrypté : " + decrypted);
        
    }
}
