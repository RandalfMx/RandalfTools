/**
 * 
 */
package mx.randalf.tools;

import java.security.NoSuchAlgorithmException;


/**
 * @author massi
 *
 */
public class ChecksumTest {

	/**
	 * 
	 */
	public ChecksumTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		MD5Tools md5 = null;
//		SHA1Tools sha1 = null;
		SHA256Tools sha256 = null;
		String parola = "";
		String value = "";
		
		try {
			
			parola = "welcome";
			
			value = MD5Tools.checkSum(parola.getBytes());
			System.out.println("MD5:\t\t"+value.length()+" -> "+value);
			
			value = SHA1Tools.checkSum(parola.getBytes());
			System.out.println("SHA1:\t\t"+value.length()+" -> "+value);
			
			sha256 = new SHA256Tools();
			value = sha256.checkSum(parola.getBytes());
			System.out.println("SHA256:\t\t"+value.length()+" -> "+value);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
