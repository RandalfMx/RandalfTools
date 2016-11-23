/**
 * 
 */
package mx.randalf.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author massi
 *
 */
public class SHA256Tools extends ChecksumTools {


	private static String CHECKSUN = "SHA-256";
	
	/**
	 * @param checkSun
	 */
	public SHA256Tools() {
		super(CHECKSUN);
	}

	public static String readMD5(String filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		ChecksumTools checkSun = null;
		
		checkSun = new ChecksumTools(CHECKSUN);
		return checkSun.readMD5File(filename);
	}

}
