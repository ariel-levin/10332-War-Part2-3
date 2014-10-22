package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.War;


/**
 * Servlet implementation class WarServlet
 */
//@WebServlet(name="/WarServlet", urlPatterns="/kuku")
@WebServlet("/WarServlet")
public class WarServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	public enum Subject {
		ADD_IRON_DOME,
		ADD_DESTRUCTOR,
		INTERCEPT,
		DESTROY
	}

	
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
		War war = (War) getServletContext().getAttribute("war");
		if (war == null) {
			war = new War("ServletWar");
			getServletContext().setAttribute("war", war);
			war.start();
		}
		int subject = Integer.parseInt(request.getParameter("subject"));
		
	}

}







