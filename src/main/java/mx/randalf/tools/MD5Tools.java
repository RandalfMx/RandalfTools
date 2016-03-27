/**
 * 
 */
package mx.randalf.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

/**
 * @author massi
 * 
 */
public class MD5Tools {

	private static String CHECKSUN = "MD5";
	
	public static byte[] createChecksum(String filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		ChecksumTools checkSun = null;
		
		checkSun = new ChecksumTools(CHECKSUN);
		return checkSun.createChecksum(filename);
	}

	public static byte[] createChecksum(InputStream fis)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		return createChecksum(fis, true);
	}

	public static byte[] createChecksum(InputStream fis, boolean closeStream)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		ChecksumTools checkSun = null;
		
		checkSun = new ChecksumTools(CHECKSUN);
		return checkSun.createChecksum(fis, closeStream);
	}

	public static String readMD5File(InputStream filename)
		throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		return readMD5File(filename,true);
	}

	public static String readMD5File(InputStream filename, boolean closeStream)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		ChecksumTools checkSun = null;
		
		checkSun = new ChecksumTools(CHECKSUN);
		return checkSun.readMD5File(filename, closeStream);
	}

	public static String readMD5File(String filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		ChecksumTools checkSun = null;
		
		checkSun = new ChecksumTools(CHECKSUN);
		return checkSun.readMD5File(filename);
	}

	public static String checkSum(byte[] testo) throws NoSuchAlgorithmException {
		ChecksumTools checkSun = null;
		
		checkSun = new ChecksumTools(CHECKSUN);
		return checkSun.checkSum(testo);
	}
}