package mx.randalf.tools;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.Vector;

import mx.randalf.tools.exception.UtilException;

import org.apache.log4j.Logger;

/**
 * Questa classe contiene funzioni di Utilita'.
 * 
 * @author Randazzo Massimiliano
 * @version 1.0
 */
public class Utils {

	private static Logger log = Logger.getLogger(Utils.class);
	
	private static long maxByteRead = 4000000;
	
	/**
	 * Questomedoto viene utilizzato per estrarre il nome del Mese
	 * 
	 * @param mese
	 * 
	 */
	public static String nomeMese(int mese) {
		Vector<String> nomeMese = new Vector<String>();
		nomeMese.add("");
		nomeMese.add("01 - Gennaio");
		nomeMese.add("02 - Febbraio");
		nomeMese.add("03 - Marzo");
		nomeMese.add("04 - Aprile");
		nomeMese.add("05 - Maggio");
		nomeMese.add("06 - Giugno");
		nomeMese.add("07 - Luglio");
		nomeMese.add("08 - Agosto");
		nomeMese.add("09 - Settembre");
		nomeMese.add("10 - Ottobre");
		nomeMese.add("11 - Novembre");
		nomeMese.add("12 - Dicembre");
		return (String) nomeMese.get(mese);
	}
	
	/**
	 * Questomedoto viene utilizzato per estrarre il nome del Mese
	 * 
	 * @param mese
	 * 
	 */
	public static String numeroMese(String mese) {
		Hashtable<String, String> numeroMese = new Hashtable<String, String>();
		numeroMese.put("gennaio", "01");
		numeroMese.put("febbraio", "02");
		numeroMese.put("marzo", "03");
		numeroMese.put("aprile", "04");
		numeroMese.put("maggio", "05");
		numeroMese.put("giugno", "06");
		numeroMese.put("luglio", "07");
		numeroMese.put("agosto", "08");
		numeroMese.put("settembre", "09");
		numeroMese.put("ottobre", "10");
		numeroMese.put("novembre", "11");
		numeroMese.put("dicembre", "12");
		return numeroMese.get(mese.toLowerCase());
	}

	/**
	 * Questo metodo a il compito di dividere una stringa nelle sue parole
	 * utilizzando il carattere specificato
	 * 
	 * @param Testo
	 *            Frase da scomporre
	 * @param key
	 *            carattere da utilizzare per la divisione
	 * @return lista parole/Frasi.
	 */
	public static Vector<String> SplitString(String Testo, String key) {
		Vector<String> risulta = new Vector<String>();
		String car;
		String parola = "";
		int i;

		try {
			for (i = 0; i < Testo.length(); i++) {
				car = Testo.substring(i, i + 1);
				if (car.equals(key)) {
					risulta.add(parola);
					parola = "";
				} else {
					parola = parola.concat(car);
				}
			}

			risulta.add(parola);
		} catch (Exception exc) {
			log.error(exc.getMessage(), exc);
		}
		return risulta;
	}

	/**
	 * Questo metodo viene utilizzato per copiare un file
	 * 
	 * @param fileOri
	 *            Nome del file di Origine
	 * @param fileDes
	 *            Nome del file di Destinazione
	 * @throws UtilException
	 */
	public static void copyFile(String fileOri, String fileDes)
			throws UtilException {
		File fr = null;
		File fo = null;
		FileInputStream br = null;
		FileOutputStream bw = null;
		byte[] testo = null;
		long offset = 0;
		int numByte = 0;

		try {
			fr = new File(fileOri);
			fo = new File(fileDes);

			if (!fr.getAbsolutePath().equals(fo.getAbsolutePath())) {
				if (!fo.getParentFile().exists()) {
					if (!fo.getParentFile().mkdirs()) {
						throw new UtilException(
								"Problema nella creazione della cartella ["
										+ fo.getParentFile().getAbsolutePath()
										+ "]");
					}
				}
				if (fo.getParentFile().getFreeSpace() > fr.length()) {
					br = new FileInputStream(fr);
					bw = new FileOutputStream(fo);
					while (offset < fr.length()) {
						if ((fr.length() - offset) < maxByteRead) {
							testo = new byte[(int)(fr.length() - offset)];
						} else {
							testo = new byte[(int) maxByteRead];
						}
						System.gc();
						numByte = br.read(testo);
						if (numByte > -1) {
							bw.write(testo);
							bw.flush();
							offset += numByte;
						}
					}
				} else {
					throw new UtilException(
							"Spazio non sufficiente per copiare il file ["
									+ fr.getAbsolutePath() + "] in ["
									+ fo.getAbsolutePath() + "] disk free: "
									+ fo.getParentFile().getFreeSpace()
									+ " byte file size: " + fr.length()
									+ " byte");
				}
			}
		} catch (UtilException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw new UtilException("File Not Found [" + e.getMessage() + "]",
					e);
		} catch (IOException e) {
			throw new UtilException("IO [" + e.getMessage() + "]", e);
		} catch (Exception e) {
			throw new UtilException("Exception [" + e.getMessage() + "]", e);
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null) {
					bw.flush();
					bw.close();
				}
			} catch (IOException e) {
				throw new UtilException("IO [" + e.getMessage() + "]", e);
			}
		}
	}

	/**
	 * Questo metodo viene utilizzato per copiare il file e validare il file
	 * copiato tramite il codice Md5
	 * 
	 * @param fileOri
	 * @param fileDes
	 * @return
	 * @throws UtilException
	 */
	public static boolean copyFileValidate(String fileOri, String fileDes)
			throws UtilException {
		return copyFileValidate(fileOri, fileDes, true);
	}

	/**
	 * Questo metodo viene utilizzato per copiare il file e validare il file
	 * copiato tramite il codice Md5
	 * 
	 * @param fileOri
	 * @param fileDes
	 * @return
	 * @throws UtilException
	 */
	public static boolean copyFileValidate(String fileOri, String fileDes, boolean overwriteExists)
			throws UtilException {
		boolean ris = false;
		String md5Ori = "";
		String md5Des = "";
		

		try {
			if (new File(fileOri).exists()){
				md5Ori = MD5Tools.readMD5File(fileOri);
				if (!overwriteExists){
					if (new File(fileDes).exists()){
						md5Des = MD5Tools.readMD5File(fileDes);
					}
					if (!md5Ori.equals(md5Des)){
						copyFile(fileOri, fileDes);
						md5Des = MD5Tools.readMD5File(fileDes);
					}
				} else {
					copyFile(fileOri, fileDes);
					md5Des = MD5Tools.readMD5File(fileDes);
				}
				ris = md5Ori.equals(md5Des);
			} else {
				throw new FileNotFoundException("Impossibile trovare il file ["+fileOri+"]");
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			throw new UtilException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
			throw new UtilException(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new UtilException(e.getMessage(), e);
		}

		return ris;
	}

	/**
	 * Questo metodo serve per la sostituzione di una Stringa con un altra
	 * Stringa
	 * 
	 * @param Testo
	 *            Testo da Convertire
	 * @param sOld
	 *            Stringa da ricercare
	 * @param sNew
	 *            Stringa da Sostituire
	 * @return Testo convertito
	 */
	public static String Replace(String Testo, String sOld, String sNew) {
		String risulta = "";
		int pos = 0;

		try {
			while (Testo.length() > 0) {
				pos = Testo.indexOf(sOld);
				if (pos > -1) {
					risulta = risulta.concat(Testo.substring(0, pos));
					risulta = risulta.concat(sNew);
					Testo = Testo.substring(pos + sOld.length());
				} else {
					risulta = risulta.concat(Testo);
					Testo = "";
				}
			}
		} catch (Exception exc) {
			log.error(exc.getMessage(), exc);
		}
		return risulta;
	}

	/**
	 * Questo metodo viene utilizzato per la generazione di una password Random
	 * 
	 * @param n
	 *            Indica il numero caratteri di cui ??? composta la password
	 * @return Indica la password generata
	 */
	public static String randomPass(int n) {
		String caratteriValidi = "";
		String temp = "";
		double icasuale = 0;
		caratteriValidi += "abcdefghijklmnopqrstuvwxyz0123456789@!";

		for (int i = 0; i < n; i++) {
			icasuale = Math.floor(Math.random() * caratteriValidi.length());
			temp += caratteriValidi.charAt((int) icasuale);
		}
		return temp;
	}

	/**
	 * Questo metodo viene utilizzato per generare la chiave normalizzata per la
	 * segnatura bibliografica
	 * 
	 * @param segnatura
	 * 
	 * @deprecated Utilizzare la classe mx.utility.ConvertText.conveSegna
	 */
	public static String conveSegna(String segnatura) {
		String ris = "";
		String numero = "";
		int nChar = 0;
		Character myChar = null;
		segnatura = segnatura.toUpperCase();
		for (int x = 0; x < segnatura.length(); x++) {
			myChar = new Character(segnatura.charAt(x));
			nChar = myChar.hashCode();
			if ((nChar > 64 && nChar < 91) || nChar == 95 || nChar == 45) {
				if (!numero.equals(""))
					ris += mettiZeri(numero);

				if (nChar == 45)
					ris += "_";
				else
					ris += segnatura.charAt(x);
				numero = "";
			} else if (nChar > 47 && nChar < 58)
				numero += segnatura.charAt(x);
			else {
				if (!numero.equals(""))
					ris += mettiZeri(numero);

				if (!ris.endsWith(" "))
					ris += " ";
				numero = "";
			}
		}
		if (!numero.equals(""))
			ris += mettiZeri(numero);
		return ris;
	}

	/**
	 * Questo metodo viene utilizzato per mettere degli zeri davanti al numero
	 * in modo da arrivare ad un totale di 6 varatteri
	 * 
	 * @param valore
	 * 
	 * @deprecated Utilizzare la classe mx.utility.ConvertText.mettiZeri
	 */
	private static String mettiZeri(String valore) {
		while (valore.length() < 6) {
			valore = "0" + valore;
		}
		return valore;
	}

}
