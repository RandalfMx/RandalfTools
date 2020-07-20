/**
 * 
 */
package mx.randalf.tools.rsync;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import mx.randalf.tools.rsync.exception.RSyncException;

/**
 * @author massi
 *
 */
public class RSync {

	private static Logger log = Logger.getLogger(RSync.class);

	/**
	 * 
	 */
	public RSync() {
	}

	public static String checkPath(File fSend){
		String fileInput = null;
		if (File.separator.equals("\\")){
			fileInput = "/cygdrive/"+fSend.getAbsolutePath().replace(":", "").replace("\\", "/");
		} else {
			fileInput = fSend.getAbsolutePath();
		}
		return fileInput;
	}

	public static void send(String rSyncPath, String rSyncPassword, String fileInput, String fileOutput) throws RSyncException{
		
		Runtime rt = null;
		String[] cmd = null;
		Process proc = null;
		InputStream stderr = null;
		InputStreamReader isrErr = null;
		BufferedReader brErr = null;
		InputStream stdout = null;
		InputStreamReader isrStd = null;
		BufferedReader brStd = null;
		String val = null;
		int exitVal = -1;
		
		try {
			rt = Runtime.getRuntime();
			cmd = new String[] { rSyncPath, 
					"-av", 
					"--progress",
					fileInput,
					fileOutput};

			proc = rt.exec(cmd, new String[]{"RSYNC_PASSWORD="+rSyncPassword});

			stderr = proc.getErrorStream();
			isrErr = new InputStreamReader(stderr);
			brErr = new BufferedReader(isrErr);

			stdout = proc.getInputStream();
			isrStd = new InputStreamReader(stdout);
			brStd = new BufferedReader(isrStd);

			while ((val = brStd.readLine()) != null) {
				log.debug("\n"+val);
			}

			while ((val = brErr.readLine()) != null) {
				log.error("\n"+val);
			}

			exitVal = proc.waitFor();

			switch (exitVal) {
			case 0:
				break;
			case 1:
				throw new RSyncException("Errore di sintassi o l'utilizzo");
			case 2:
				throw new RSyncException("Protocollo di incompatibilità");
			case 3:
				throw new RSyncException(
						"Errori di selezione dei file di input / output, dirs");
			case 4:
				throw new RSyncException(
						"L'azione richiesta non è supportata: un tentativo è stato fatto per manipolare file a 64 bit su una piattaforma che non li può sostenere, o un'opzione stato precisato che è supportato dal client e non dal server.");
			case 5:
				throw new RSyncException(
						"Errore durante l'avvio del protocollo client-server");
			case 6:
				throw new RSyncException(
						"Daemon in grado di aggiungere al log-file");
			case 10:
				throw new RSyncException("Errore in socket I/O");
			case 11:
				throw new RSyncException("Errore in file I/O");
			case 12:
				throw new RSyncException(
						"Errore nei rsync flusso di dati del protocollo");
			case 13:
				throw new RSyncException(
						"Errori con diagnostica del programma");
			case 14:
				throw new RSyncException("Errore nel codice IPC");
			case 20:
				throw new RSyncException("Ricevuto SIGUSR1 o SIGINT");
			case 21:
				throw new RSyncException(
						"Qualche errore restituito da waitpid()");
			case 22:
				throw new RSyncException(
						"Buffer di memoria centrale che ripartisce errore");
			case 23:
				throw new RSyncException(
						"Trasferimento parziale a causa di un errore");
			case 24:
				throw new RSyncException(
						"Trasferimento parziale a causa di file di origine scomparsi");
			case 25:
				throw new RSyncException(
						"Il limite --max-delete a fermato eliminazioni");
			case 30:
				throw new RSyncException(
						"Timeout nei dati di invio / ricezione");
			case 255:
				throw new RSyncException(
						"Autorizzazione negata, riprova.");
			default:
				throw new RSyncException(
						"Errore generico ["+exitVal+"]");
			}
		} catch (IOException e) {
			throw new RSyncException(e.getMessage(), e);
		} catch (InterruptedException e) {
			throw new RSyncException(e.getMessage(), e);
		} catch (RSyncException e) {
			throw e;
		} finally {
			try {
				if (brStd!= null){
					brStd.close();
				}
				if (isrStd != null){
					isrStd.close();
				}
				if (stdout != null){
					stdout.close();
				}
				if (brErr != null){
					brErr.close();
				}
				if (isrErr != null){
					isrErr.close();
				}
				if (stderr != null){
					stderr.close();
				}
				log.info("\n"+"File inviato");
			} catch (IOException e) {
				throw new RSyncException(e.getMessage(), e);
			}
		}
	}
}
