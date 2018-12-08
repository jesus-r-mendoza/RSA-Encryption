package rsa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RSAGenKey {
    
    public static void main(String[] args) {
        System.out.println();
        SecureRandom sr = new SecureRandom();
        switch (args.length) {
            case 1:
                useSize(args[0], sr);
                break;
            case 3:
                useGiven(args[0], args[1], args[2], sr);
                break;
            default:
                System.out.println("\n*** ERROR : Given an invalid amount of arguments, need 1 or 3 ; Given " + args.length + " ***");
        }
        
    }
    
    protected static void useSize(String k, SecureRandom sr) {
        
        int len = Integer.parseInt(k);
        
        BigInteger p, q, n, e, d, phiOfN;
        
        p = BigInteger.probablePrime(len, sr);
        q = p.nextProbablePrime();
        
        n = p.multiply(q);
        phiOfN = minusOne(p).multiply(minusOne(q));
        
        e = coPrime(phiOfN, sr);
        d = e.modInverse(phiOfN);
               
        writeFile("rsa/outputs/pub_key.txt", n.toString(), e.toString(), true);
        writeFile("rsa/outputs/pri_key.txt", n.toString(), d.toString(), false);
    
    }
    
    private static void useGiven(String pStr, String qStr, String eStr, SecureRandom sr) {
        
        BigInteger p, q, e, d, n, phiOfN;
        
        p = new BigInteger(pStr);
        q = new BigInteger(qStr);
        
        n = p.multiply(q);
        phiOfN = minusOne(p).multiply(minusOne(q));
        
        e = new BigInteger(eStr);
        d = e.modInverse(phiOfN);
        
        writeFile("rsa/outputs/pub_key.txt", n.toString(), e.toString(), true);
        writeFile("rsa/outputs/pri_key.txt", n.toString(), d.toString(), false);
        
    }
    
    private static BigInteger minusOne(BigInteger b) {
        return b.subtract(BigInteger.ONE);
    }
    
    private static BigInteger coPrime(BigInteger b, SecureRandom rnd) {
        int bits = b.bitLength()-1;
        BigInteger e;
        do {
            e = BigInteger.probablePrime(bits, rnd);
        } while ( ! (b.gcd(e).equals(BigInteger.ONE)) );
        return e;
    }
    
    private static void writeFile(String fileName, String n, String x, boolean isE) {
        
        File fileOut = new File(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut))) {
            
            if (isE)
                writer.write("e = " + x);
            else 
                writer.write("d = " + x);
            writer.newLine();
            writer.write("n = " + n);
            
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        
        System.out.println("wrote file: " + fileName);
    }

}