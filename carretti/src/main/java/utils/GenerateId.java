package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GenerateId implements UniqueCodeGenerator{
	
	 SecureRandom prng;
	
	public GenerateId() {
		try {
				 //Initialize SecureRandom
		        //This is a lengthy operation, to be done only upon
		       //initialization of the application
			this.prng = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public String generateRandomId() {
		try {
		      //generate a random number
		      String randomNum = new Integer(this.prng.nextInt()).toString();
		      //get its digest
		      MessageDigest sha = MessageDigest.getInstance("SHA-1");
		      byte[] result =  sha.digest(randomNum.getBytes());
			  StringBuffer sb = new StringBuffer();
		      for (byte b : result) {
					sb.append(String.format("%02x", b & 0xff));
				}
				return sb.toString();
			}
		    catch (NoSuchAlgorithmException ex) {
		      System.err.println(ex);
		    }
		return null;
	}
}