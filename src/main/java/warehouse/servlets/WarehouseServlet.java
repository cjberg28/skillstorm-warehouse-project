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
import warehouse.models.NotFound;
import warehouse.models.WarehouseObject;

@WebServlet(urlPatterns= {"/add/*","/delete/*","/update/*","/find/*"})
public class WarehouseServlet extends HttpServlet {

	private static final long serialVersionUID = 2616722407793908813L;
	WarehouseDAO dao = new WarehouseDAOImplementation();
	ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Processes the GET request for finding by slotId or finding all by type.
	 * Get requests made by type will be in the form of a dropdown selector on the front-end UI.
	 * So, we can assume that the string we get will be valid (if we're not searching by id instead).
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType("application/json");
			
			//Ex: GET request from localhost:8080/warehouse-project-berg/find/example gives /example
			String url = req.getPathInfo();
			String[] splitString = url.split("/");//["","example"]
			String type = splitString[1];
			
			//System.out.println(url);
			//System.out.println(type);
			
			Integer id = null;//Use as a flag.
			try {
				id = Integer.parseInt(type);//If this fails, id will be null.
			} catch (NumberFormatException e) {
				//Not an integer id, try type as a String for type.
			}
			
			if (id != null) {//Id was used as search criterion
				if (id > 0) {//Valid id
					WarehouseObject warehouseObject = dao.findBySlotId(id);
					resp.getWriter().print(mapper.writeValueAsString(warehouseObject));
				} else {
					throw new Exception("Invalid slotId used in GET request.");
				}
			} else {//Type was used as a search criterion. We can assume the string is a valid type.
				List<WarehouseObject> warehouseObjects = dao.findAllOfType(type);
				resp.getWriter().print(mapper.writeValueAsString(warehouseObjects));
			}
			
			
		} catch (Exception e) { //Valid slotId or type not included in url, so return all objects.
			List<WarehouseObject> warehouseObjects = dao.findAll();
			//System.out.println(warehouseObjects);
			resp.getWriter().print(mapper.writeValueAsString(warehouseObjects));
		}
	}
	
	/**
	 * Processes the POST request for warehouse objects.
	 * Assumes the request body JSON is valid.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		WarehouseObject warehouseObject = mapper.readValue(reqBody, WarehouseObject.class);
		
		warehouseObject = dao.addToDatabase(warehouseObject); //If the ID changed.
		
		if (warehouseObject != null) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(warehouseObject));
			resp.setStatus(201); // The default is 200. 201 is success - new object created.
		} else {
			resp.setStatus(400);
			resp.getWriter().print(mapper.writeValueAsString(new NotFound("Unable to create warehouse object.")));
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream reqBody = req.getInputStream();
		WarehouseObject warehouseObject = mapper.readValue(reqBody, WarehouseObject.class);
		
		dao.update(warehouseObject);
		
		
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
