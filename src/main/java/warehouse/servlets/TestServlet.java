package warehouse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/home")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 7259527207691606764L;

	/**
	 * When a GET request is made to this servlet, it simply redirects them to the main page.
	 * This never occurs on the front end; it was simply used for testing.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Redirect them to the HTML page
		resp.sendRedirect("/warehouse-project-berg");
	}
}
