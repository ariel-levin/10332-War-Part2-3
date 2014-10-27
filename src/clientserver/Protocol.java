package clientserver;

import java.io.Serializable;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class Protocol implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum Subject {
		ADD_LAUNCHER,
		LAUNCH_MISSILE,
		WAR_DESTINATIONS,
		SUCCESS,
		FAILURE,
		DISCONNECT
	}

	private Subject subject;
	private Object[] data;

	
	public Protocol(Subject subject) {
		this.subject = subject;
	}

	public Protocol(Subject subject, Object... data) {
		this(subject);
		this.data = data;
	}

	public Subject getSubject() {
		return subject;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

}

