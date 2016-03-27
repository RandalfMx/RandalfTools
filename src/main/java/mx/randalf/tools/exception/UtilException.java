/**
 * 
 */
package mx.randalf.tools.exception;

/**
 * @author massi
 *
 */
public class UtilException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2337514141500414159L;

	/**
	 * 
	 */
	public UtilException() {
	}

	/**
	 * @param arg0
	 */
	public UtilException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public UtilException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public UtilException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
