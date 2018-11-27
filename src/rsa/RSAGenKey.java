package rsa;

import java.math.BigInteger;
import java.util.Random;

public class RSAGenKey {
    
    public static void main(String[] args) {
        
        switch (args.length) {
            case 1:
                useSize(args[0]);
                break;
            case 3:
                useGiven(args[0], args[1], args[2]);
                break;
            default:
                System.out.println("\n*** ERROR : Given an invalid amount of arguments, nead 1 or 3 ; Given " + args.length + " ***");
        }
        
    }
    
    private static void useSize(String k) {
        System.out.println(k);
    }
    
    private static void useGiven(String p, String q, String e) {
        System.out.println(p+", "+q+", "+e);
    }
        
}