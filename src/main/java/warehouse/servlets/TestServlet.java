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

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. Send the message itself
		// 2. Redirect them to the HTML page
		System.out.println("Hello Servlet!");
		resp.sendRedirect("/warehouse-project-berg");
	}
}
