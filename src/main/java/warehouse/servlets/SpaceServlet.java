package warehouse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import warehouse.daos.WarehouseDAO;
import warehouse.daos.WarehouseDAOImplementation;
import warehouse.models.JSONResultMessage;

@WebServlet(urlPatterns="/space")
public class SpaceServlet extends HttpServlet {

	private static final long serialVersionUID = 7839218196086877057L;
	WarehouseDAO dao = new WarehouseDAOImplementation();
	ObjectMapper mapper = new ObjectMapper();

	/**
	 * When a GET request is made to this servlet, it returns the remaining space
	 * left in the warehouse/database.
	 * @param req (HttpServletRequest, the Http Request)
	 * @param resp (HttpServletResponse, the Http Response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		float remainingSpace = dao.getRemainingSpace();//Could be -1 if something went wrong.
		
		if (remainingSpace < 0) {//This should never happen - Checks exist to prevent POST/PUT to go over max space.
			resp.setStatus(500);//This would be an error on the server's side, since it is just a sum.
			resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Sum failed - Something went wrong on the server side.")));
		} else {
			String remainingSpaceString = String.format("Space Remaining: %.2f sqft", remainingSpace);
			resp.setStatus(200);
			resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage(remainingSpaceString)));
		}
	}
	
}
