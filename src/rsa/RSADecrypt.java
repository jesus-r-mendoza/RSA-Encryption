package rsa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Hashtable;

public class RSADecrypt {
	// Run with  java -cp . rsa/RSADecrypt rsa/outputs/test.enc rsa/outputs/pri_key.txt
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println(decryptionProcess(RSAEncrypt.encryptionProcess("Hello World")));
		} else if(args.length >= 2) {
			decryptionProcess(args[0], getPrivateKey(args[1]));
		}
		
	}
	public static void decryptionProcess(String ciphertextLocation, Hashtable<Character, BigInteger> keys) {

		File file = new File(ciphertextLocation); 
		  
		  BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
		
		String ciphertext;
		
		try {
			ciphertext = br.readLine();
			File fileOut = new File("rsa/outputs/test.dec");
		    BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut));
			
			while(ciphertext != null) {
		
				String plaintext = "";
				
				String[] cipherList = ciphertext.split(" ");
				for(String block: cipherList) {
					String decryptedBlock = decrypt(new BigInteger(block), keys);
					while(decryptedBlock.length() < 6) {
						decryptedBlock = "0" + decryptedBlock;
					}
					
					int value1 = (Integer.parseInt(decryptedBlock.substring(0, 2)) + 65);
					value1 = value1 != 91 ? value1 : 32;
					plaintext += (char)value1;
					
					int value2 = (Integer.parseInt(decryptedBlock.substring(2, 4)) + 65);
					value2 = value2 != 91 ? value2 : 32;
					plaintext += (char) value2;
					
					int value3 = (Integer.parseInt(decryptedBlock.substring(4)) + 65);
					value3 = value3 != 91 ? value3 : 32;
					plaintext += (char) value3;		
				}			
				// Write about plaintext
				 writer.write(plaintext);
	            ciphertext = br.readLine();
	            if(ciphertext != null) {
				    writer.newLine();
	            }
			}
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(2);
		}
		
	}

	public static String decryptionProcess(String ciphertext) {
		Hashtable<Character, BigInteger> keys = getPrivateKey();
		
		String plaintext = "";
		
		String[] cipherList = ciphertext.split(" ");
		for(String block: cipherList) {
			String decryptedBlock = decrypt(new BigInteger(block), keys);
			while(decryptedBlock.length() < 6) {
				decryptedBlock = "0" + decryptedBlock;
			}
			
			int value1 = (Integer.parseInt(decryptedBlock.substring(0, 2)) + 65);
			value1 = value1 != 91 ? value1 : 32;
			plaintext += (char)value1;
			
			int value2 = (Integer.parseInt(decryptedBlock.substring(2, 4)) + 65);
			value2 = value2 != 91 ? value2 : 32;
			plaintext += (char) value2;
			
			int value3 = (Integer.parseInt(decryptedBlock.substring(4)) + 65);
			value3 = value3 != 91 ? value3 : 32;
			plaintext += (char) value3;		
		}
		
		return plaintext;
		
	}
	
	public static String decrypt(BigInteger block, Hashtable<Character, BigInteger> key) {		
		BigInteger ciphertext = block.modPow(key.get('d'),key.get('n')); 
		return "" + ciphertext;
	}
	
	public static Hashtable<Character, BigInteger> getPrivateKey(String src) {
		 File file = new File(src); 
		  
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
			  values.put('d', new BigInteger(st.substring(4).trim())); 
			  
			  st = br.readLine();
			  values.put('n', new BigInteger(st.substring(4).trim())); 
			  
		  } catch(IOException e) {
			  System.out.println(e);
			  System.exit(1);
		  }  
		  
	
		return values;
	}
	
	public static Hashtable<Character, BigInteger> getPrivateKey() {
		 File file = new File("src/rsa/outputs/pri_key.txt"); 
		  
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
			  values.put('d', new BigInteger(st.substring(4).trim())); 
			  
			  st = br.readLine();
			  values.put('n', new BigInteger(st.substring(4).trim())); 
			  
		  } catch(IOException e) {
			  System.out.println(e);
			  System.exit(1);
		  }  
		  
	
		return values;
	}

}
