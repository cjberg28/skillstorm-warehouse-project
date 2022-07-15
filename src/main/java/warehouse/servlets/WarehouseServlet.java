package warehouse.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import warehouse.daos.WarehouseDAO;
import warehouse.daos.WarehouseDAOImplementation;
import warehouse.models.WarehouseObject;

@WebServlet(urlPatterns="/add")//TODO Change to appropriate URL's.
public class WarehouseServlet extends HttpServlet {

	private static final long serialVersionUID = 2616722407793908813L;
	WarehouseDAO dao = new WarehouseDAOImplementation();
	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			//TODO Figure out what my Tomcat server url ultimately is, and then modify urlPatterns and determine the path info.
			String url = req.getPathInfo();// 8080/warehouse-app/
		} catch (Exception e) { //slotId or type not included in url, so return all objects.
			List<WarehouseObject> warehouseObjects = dao.findAll();
			System.out.println(warehouseObjects);
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(warehouseObjects));
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		WarehouseObject warehouseObject = mapper.readValue(reqBody, WarehouseObject.class);
		
		warehouseObject = dao.addToDatabase(warehouseObject); //If the ID changed.
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
