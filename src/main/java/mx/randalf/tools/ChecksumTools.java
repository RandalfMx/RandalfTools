/**
 * 
 */
package mx.randalf.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author massi
 * 
 */
class ChecksumTools {

	private String checkSun = null;
	public ChecksumTools(String checkSun){
		this.checkSun = checkSun;
	}

	public byte[] createChecksum(String filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		byte[] buffer = null;
		InputStream fis = null;

		try {
			fis = new FileInputStream(filename);

			buffer = createChecksum(fis);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fis != null){
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return buffer;
	}

	public byte[] createChecksum(File filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		byte[] buffer = null;
		InputStream fis = null;

		try {
			fis = new FileInputStream(filename);

			buffer = createChecksum(fis);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fis != null){
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return buffer;
	}

	public byte[] createChecksum(InputStream fis)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		return createChecksum(fis, true);
	}

	public byte[] createChecksum(InputStream fis, boolean closeStream)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		byte[] buffer = null;
		MessageDigest complete = null;
		int numRead;

		try {

			buffer = new byte[1024];
			complete = MessageDigest.getInstance(checkSun);

			do {
				numRead = fis.read(buffer);
				if (numRead > 0) {
					complete.update(buffer, 0, numRead);
				}
			} while (numRead != -1);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (closeStream){
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return complete.digest();
	}

	public String readMD5File(InputStream filename)
		throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		return readMD5File(filename,true);
	}

	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public String readMD5File(InputStream filename, boolean closeStream)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		byte[] b = null;
		String result = "";

		try {
			b = createChecksum(filename, closeStream);
			for (int i = 0; i < b.length; i++) {
				result += Integer.toString((b[i] & 0xff) + 0x100, 16)
						.substring(1);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return result;
	}

	public String readMD5File(String filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		byte[] b = null;
		String result = "";

		try {
			b = createChecksum(filename);
			for (int i = 0; i < b.length; i++) {
				result += Integer.toString((b[i] & 0xff) + 0x100, 16)
						.substring(1);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return result;
	}

	public String readMD5File(File filename)
			throws FileNotFoundException, NoSuchAlgorithmException, IOException {
		byte[] b = null;
		String result = "";

		try {
			b = createChecksum(filename);
			for (int i = 0; i < b.length; i++) {
				result += Integer.toString((b[i] & 0xff) + 0x100, 16)
						.substring(1);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (NoSuchAlgorithmException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return result;
	}

	public String checkSum(byte[] testo) throws NoSuchAlgorithmException {
		MessageDigest complete = null;
		String result = "";
		byte[] b = null;

		try {
			complete = MessageDigest.getInstance(checkSun);
			complete.update(testo);
			b = complete.digest();

			for (int i = 0; i < b.length; i++) {
				result += Integer.toString((b[i] & 0xff) + 0x100, 16)
						.substring(1);
			}
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
		return result;
	}
}