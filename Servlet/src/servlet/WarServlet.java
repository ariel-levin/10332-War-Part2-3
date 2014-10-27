package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.WarXMLReader;
import model.War;


/**
 * Servlet implementation class WarServlet
 * 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 */
@WebServlet(name = "WarServlet", urlPatterns = {"/WarServlet"})
public class WarServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private static final int START_WAR = 0;
	private static final int ADD_IRON_DOME = 1;
	private static final int ADD_DESTRUCTOR = 2;
	private static final int INTERCEPT_FORM = 3;
	private static final int DESTROY_FORM = 4;
	private static final int END_WAR = 5;
	private static final int INTERCEPT_MISSILE = 6;
	private static final int DESTROY_LAUNCHER = 7;
	
	private War war = null;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WarServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter output = response.getWriter();
		
		int subject = -1;
		try {
			subject = Integer.parseInt(request.getParameter("subject"));
		} catch (java.lang.NumberFormatException e) {
			System.out.println("\nSome error with getting parameter from html\n");
		}
		
		switch (subject) {
		
			case START_WAR:				startWar(output);
										break;
		
			case ADD_IRON_DOME:			addIronDome(output);
										break;
								
			case ADD_DESTRUCTOR:		addDestructor(output, request);
										break;
									
			case INTERCEPT_FORM:		createInterceptForm(output);
										break;
									
			case DESTROY_FORM:			createDestroyForm(output);
										break;
									
			case END_WAR:				endWar(output);
										break;
									
			case INTERCEPT_MISSILE:		interceptMissile(output, request);
										break;
									
			case DESTROY_LAUNCHER:		destroyLauncher(output, request);
										break;
									
		}
		
		output.close();
	}

	private void startWar(PrintWriter output) {
		String msg = "";
		
		if (war != null) {
			msg = "ERROR: The War has already started";
			printResponse(msg, output);
			return;
		}

		war = new War("ServletWar");
		getServletContext().setAttribute("war", war);
		war.start();

		msg = "The War has started Successfully";
		printResponse(msg, output);
	}
	
	private void addIronDome(PrintWriter output) {
		String msg = "";
		
		if (war == null) {
			msg = "ERROR: Please start war first";
			printResponse(msg, output);
			return;
		}
		
		String ironDomeID = war.addIronDome();
		
		if (ironDomeID == null)
			msg = "Iron Dome failed to add..";
		else
			msg = "Iron Dome " + ironDomeID + " was added Successfully";
		
		printResponse(msg, output);
	}
	
	private void addDestructor(PrintWriter output, HttpServletRequest request) {
		String msg = "";
		
		if (war == null) {
			msg = "ERROR: Please start war first";
			printResponse(msg, output);
			return;
		}
		
		String type = (String)request.getParameter("cbType");
		String destructorID = null;
		
		if (type != null)
			destructorID = war.addDefenseLauncherDestructor(type);

		if (type==null || destructorID==null)
			msg = "Launcher Destructor failed to add..";
		else
			msg = "Launcher Destructor " + destructorID + " was added Successfully";
		
		printResponse(msg, output);
	}
	
	private void createInterceptForm(PrintWriter output) {
		
		String msg = "";
		
		if (war == null) {
			msg = "ERROR: Please start war first";
			printResponse(msg, output);
			return;
		}
		
		Vector<String> missiles = war.getAllDuringFlyMissilesIds();
		
		if (missiles == null) {
			msg = "ERROR: No Missiles On-Air to Intercept";
			printResponse(msg, output);
			return;
		}
		
		output.println("<html>");
		output.println("<head>");
		output.println("<title>Intercept Missile</title>");
		output.println("</head>");
		output.println("<body>");
		output.println("<form method='get' action='WarServlet'>");
		output.println("<input checked='checked' name='subject' type='radio' value='6' style='display:none;'/>");
		output.println("Select Missile to Intercept: &nbsp; "
						+ "<select name='cbMissile'>");
		
		for (String m : missiles) {
			output.println("<option value='" + m + "'>" + m + "</option>");
		}
		
		output.println("</select><br><br>");
		output.println("&nbsp; <input type='submit' value='Intercept'/> &nbsp; &nbsp; &nbsp; ");
		output.println("<a href='/Servlet'><input type='button' value='Back'></a>");
		output.println("</form>");
		output.println("</body>");
		output.println("</html>");
	}

	private void createDestroyForm(PrintWriter output) {
		
		String msg = "";
		
		if (war == null) {
			msg = "ERROR: Please start war first";
			printResponse(msg, output);
			return;
		}
		
		Vector<String> launchers = war.getAllVisibleLaunchersIds();
		
		if (launchers == null) {
			msg = "ERROR: No Launchers available to Destroy";
			printResponse(msg, output);
			return;
		}
		
		output.println("<html>");
		output.println("<head>");
		output.println("<title>Destroy Launcher</title>");
		output.println("</head>");
		output.println("<body>");
		output.println("<form method='get' action='WarServlet'>");
		output.println("<input checked='checked' name='subject' type='radio' value='7' style='display:none;'/>");
		output.println("Select Launcher to Destroy: &nbsp; "
						+ "<select name='cbLauncher'>");
		
		for (String l : launchers) {
			output.println("<option value='" + l + "'>" + l + "</option>");
		}
		
		output.println("</select><br><br>");
		output.println("&nbsp; <input type='submit' value='Destroy'/> &nbsp; &nbsp; &nbsp; ");
		output.println("<a href='/Servlet'><input type='button' value='Back'></a>");
		output.println("</form>");
		output.println("</body>");
		output.println("</html>");
	}
	
	private void endWar(PrintWriter output) {
		String msg = "";
		
		if (war == null) {
			msg = "ERROR: Please start war first";
			printResponse(msg, output);
			return;
		}
		
		WarXMLReader.stopAllThreads();
		
		//notify the war
		synchronized (war) {
			war.notify();
		}
		
		war = null;
		
		msg = "The War has ended Successfully";
		printResponse(msg, output);
	}
	
	private void interceptMissile(PrintWriter output, HttpServletRequest request) {
		String selectedMissile = (String)request.getParameter("cbMissile");
		
		if (selectedMissile != null) {
			war.interceptGivenMissile(selectedMissile);
			String msg = "Request to Intercept Missile " + selectedMissile
					+ " completed successfully";
			printResponse(msg, output);
		}
	}
	
	private void destroyLauncher(PrintWriter output, HttpServletRequest request) {
		String selectedLauncher = (String)request.getParameter("cbLauncher");
		
		if (selectedLauncher != null) {
			war.interceptGivenLauncher(selectedLauncher);
			String msg = "Request to Destroy Launcher " + selectedLauncher
					+ " completed successfully";
			printResponse(msg, output);
		}
	}
	
	private void printResponse(String msg, PrintWriter output) {
		output.println("<html>");
		output.println("<head>");
		output.println("<title>Add Iron Dome</title>");
		output.println("</head>");
		output.println("<body>");
		output.println(msg + "<br><br>");
		output.println("<a href='/Servlet'><input type='button' value='Back'></a>");
		output.println("</body>");
		output.println("</html>");
	}
	
}

