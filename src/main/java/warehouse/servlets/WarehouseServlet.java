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
import warehouse.models.JSONResultMessage;
import warehouse.models.WarehouseObject;


//TODO These should be separated and made into 4 different servlets. Will do if extra time.
@WebServlet(urlPatterns= {"/add/*","/delete/*","/update/*","/find/*"})
public class WarehouseServlet extends HttpServlet {

	private static final long serialVersionUID = 2616722407793908813L;
	WarehouseDAO dao = new WarehouseDAOImplementation();
	ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Processes the GET request for finding by slotId or finding all by type.
	 * Get requests made by type will be in the form of a dropdown selector on the front-end UI.
	 * So, we can assume that the string we get will be valid (if we're not searching by id instead).
	 * @param req (HttpServletRequest, the Http Request)
	 * @param resp (HttpServletResponse, the Http Response)
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
	 * @param req (HttpServletRequest, the Http Request)
	 * @param resp (HttpServletResponse, the Http Response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			InputStream reqBody = req.getInputStream();
			WarehouseObject warehouseObject = mapper.readValue(reqBody, WarehouseObject.class);
			float spaceRemaining = dao.getRemainingSpace();
			
			if (warehouseObject.getSpaceRequired() > spaceRemaining) {
				throw new IllegalArgumentException("New object to be added would go over maximum capacity.");
			}
			
			warehouseObject = dao.addToDatabase(warehouseObject); //If the ID changed.
			
			if (warehouseObject != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(warehouseObject));
				resp.setStatus(201); // The default is 200. 201 is success - new object created.
			} else {
				resp.setStatus(200);
				resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Addition to warehouse failed - Object has missing or invalid parameters.")));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			resp.setStatus(200);
			resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Addition to warehouse failed - Object addition would exceed maximum capacity.")));
		}
	}
	
	/**
	 * Processes the PUT request for warehouse objects.
	 * Will only return JSONResultMessages.
	 * @param req (HttpServletRequest, the Http Request)
	 * @param resp (HttpServletResponse, the Http Response)
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			InputStream reqBody = req.getInputStream();
			WarehouseObject warehouseObject = mapper.readValue(reqBody, WarehouseObject.class);
			float spaceRemaining = dao.getRemainingSpace();
			
			//Could be null if user tried updating an object but entered a 0 or negative slotId.
			//Could also be null if the object doesn't exist in the warehouse.
			WarehouseObject oldWarehouseObject = dao.findBySlotId(warehouseObject.getSlotId());
			if (oldWarehouseObject == null) {
				throw new IllegalArgumentException("oldWarehouseObject is null");
			}
			
			//If proposed new object space - current object space > space remaining
			//i.e., by updating the object, you would be needing more space than capacity allows for
			if (warehouseObject.getSpaceRequired() - oldWarehouseObject.getSpaceRequired() > spaceRemaining) {
				throw new Exception("Updated space exceeds maximum capacity.");
			}
			
			boolean updateSuccessful = dao.update(warehouseObject);//Flag for update attempt.
			if (updateSuccessful) {//Successful update.
				resp.setContentType("application/json");//Necessary?
				resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Update successful.")));
				resp.setStatus(200);
			} else {
				resp.setStatus(200);
				resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Update failed - Something went wrong.")));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			resp.setStatus(200);
			resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Update failed - The object may not exist in the warehouse, "
					+ "or the information may be entered incorrectly.")));
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(200);
			resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Update failed - Proposed new space required for the object would exceed maximum capacity.")));
		}
	}
	
	/**
	 * Processes the DELETE request for warehouse objects.
	 * Only cares about the id.
	 * @param req (HttpServletRequest, the Http Request)
	 * @param resp (HttpServletResponse, the Http Response)
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Deletion URL request: 8080/warehouse-project-berg/delete/{slotId}
		String url = req.getPathInfo();
		String[] splitString = url.split("/");//["","{slotId}"]
		String type = splitString[1];
		Integer id = null;
		
		try {
			id = Integer.parseInt(type);
		} catch (NumberFormatException e) {
			//Invalid deletion request.
		}
		
		if (id == null) {
			resp.setStatus(200);
			resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Deletion failed - Invalid Slot ID provided.")));
		} else {
			boolean deleteSuccessful = dao.delete(id);
			if (deleteSuccessful) {
				resp.setStatus(200);
				resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Deletion successful.")));
			} else {//slotId is valid, but object doesn't exist in warehouse.
				resp.setStatus(200);
				resp.getWriter().print(mapper.writeValueAsString(new JSONResultMessage("Deletion failed - Object does not exist in warehouse.")));
			}
		}
	}
}
