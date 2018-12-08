package rsa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Hashtable;

public class RSAEncrypt {
	// Run with java -cp . rsa/RSAEncrypt testData/test.txt rsa/outputs/pub_key.txt
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println(RSADecrypt.decryptionProcess(encryptionProcess("go online at all during your ban")));
		} else if(args.length >= 2) {
			encryptionProcess(args[0], getPublicKey(args[1]));
		}
		
	}
	
	public static void encryptionProcess(String plaintextLocation, Hashtable<Character, BigInteger> key) {				
		File file = new File(plaintextLocation); 
		  
		  BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
		
		String plaintext;
		
		try {
			plaintext = br.readLine();
			File fileOut = new File("rsa/outputs/test.enc");
		    BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut));
			
			while(plaintext != null) {
				plaintext = plaintext.toUpperCase();
				String cipherText = "";
				for(int i = 0;  i < plaintext.length(); i+=3) {
					String block = "";
					for(int j = 0; j < 3; j++) {
						if(i + j < plaintext.length() && (Character.isLetter(plaintext.charAt(i + j)) || plaintext.charAt(i+j) == ' ')) {
							if(plaintext.charAt(i+j) == 32) {
								block += "26";
							} else {
								block += String.format("%02d", (((int) plaintext.charAt(i+j)) - 65));
							}
						} else {
							block += "26";
						}
					}
					cipherText += encrypt(new BigInteger(block), key) + " ";
				}
				 writer.write(cipherText);
	            plaintext = br.readLine();
	            if(plaintext != null) {
				    writer.newLine();
	            }
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static String encryptionProcess(String plaintext) {
		Hashtable<Character, BigInteger> key = getPublicKey();
		
		plaintext = plaintext.toUpperCase();
		String cipherText = "";
		for(int i = 0;  i < plaintext.length(); i+=3) {
			String block = "";
			for(int j = 0; j < 3; j++) {
				if(i + j < plaintext.length() && (Character.isLetter(plaintext.charAt(i + j)) || plaintext.charAt(i+j) == ' ')) {
					if(plaintext.charAt(i+j) == 32) {
						block += "26";
					} else {
						block += String.format("%02d", (((int) plaintext.charAt(i+j)) - 65));
					}
				}else {
					block += "26";
				}
			}
			cipherText += encrypt(new BigInteger(block), key) + " ";

		}
		
		
		
		return cipherText;
		
	}
	
	public static String encrypt(BigInteger block, Hashtable<Character, BigInteger> key) {		
		BigInteger ciphertext = block.modPow(key.get('e'),key.get('n')); 
		return "" +ciphertext;
	}
	
	public static void generateKey(int size) {
		RSAGenKey.useSize("" + size, new SecureRandom());
	}
	

	public static Hashtable<Character, BigInteger> getPublicKey(String location) {
		 File file = new File(location); 
		  
		  BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		  Hashtable<Character, BigInteger> values = new Hashtable<>();
		  
		  String st; 
		  
		  try {
			  st = br.readLine();
			  values.put('e', new BigInteger(st.substring(4).trim())); 
			  
			  st = br.readLine();
			  values.put('n', new BigInteger(st.substring(4).trim())); 
			  
		  } catch(IOException e) {
			  System.out.println(e);
			  System.exit(1);
		  }  
		  
	
		return values;
	}
	
	public static Hashtable<Character, BigInteger> getPublicKey() {
		 File file = new File("src/rsa/outputs/pub_key.txt"); 
		  
		  BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		  Hashtable<Character, BigInteger> values = new Hashtable<>();
		  
		  String st; 
		  
		  try {
			  st = br.readLine();
			  values.put('e', new BigInteger(st.substring(4).trim())); 
			  
			  st = br.readLine();
			  values.put('n', new BigInteger(st.substring(4).trim())); 
			  
		  } catch(IOException e) {
			  System.out.println(e);
			  System.exit(1);
		  }  		  
	
		return values;
	}
}
