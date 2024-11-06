package org.openjfx;
import java.math.BigInteger;
public class Test2RSA {
    


    public static void main(String[] args) {
        // Choix des grands nombres premiers
        BigInteger p = new BigInteger("9516312367584655427598");
        BigInteger q = new BigInteger("7414207109443139983113");

        // Calcul de n et phi(n)
        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Choix de e
        BigInteger e = new BigInteger("65537");

        BigInteger d = e.modInverse(phi);

        // Affichage des valeurs
        System.out.println("n = " + n);
        System.out.println("e = " + e);
        System.out.println("d = " + d);

    }
}


