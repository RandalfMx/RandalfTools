/**
 * 
 */
package mx.randalf.tools.rsync.exception;

/**
 * @author massi
 *
 */
public class RSyncException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4204654460692025627L;

	/**
	 * @param arg0
	 */
	public RSyncException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public RSyncException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
