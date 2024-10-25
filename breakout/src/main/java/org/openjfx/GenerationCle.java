package org.openjfx;


import java.math.BigInteger;
import java.security.SecureRandom;

public class GenerationCle {

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

    public static void main(String [] args){
        //System.out.println(n + "\n -----" + e + "\n ----------" + d);
    }





}