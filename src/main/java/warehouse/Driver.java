package warehouse;

import java.util.LinkedList;
import java.util.List;

import warehouse.daos.WarehouseDAOImplementation;
import warehouse.models.WarehouseObject;

/**
 * This class is simply a test file for the back end. It is not used in the
 * final product.
 */
public class Driver {

	public static void main(String[] args) {
//		WarehouseObject w = new WarehouseObject(7,500.0f,"Box of Tents","SHELTER");
//		
//		WarehouseDAOImplementation dao = new WarehouseDAOImplementation();
//		
////		dao.addToDatabase(w);
//
//		
//		List<WarehouseObject> objs = dao.findAll();
//		
//		
//		System.out.println("findAll():");
//		for (WarehouseObject o : objs) {
//			System.out.println(o);
//		}
//		
//		System.out.println("FindAllOfType():");
//		System.out.println(dao.findAllOfType("MISC"));
//		
//		System.out.println("FindBySlotId():");
//		System.out.println(dao.findBySlotId(1));
//		
//		System.out.println("update():");
//		dao.update(w);
//		System.out.println(dao.findBySlotId(3));
//		
////		System.out.println("delete(wObj):");
////		dao.delete(w);
//		System.out.println("delete(id):");
//		dao.delete(3);
		int id = -1;
		String resp = "";
		try {
			resp = "memes";
			id = Integer.parseInt("2meme5me");
		} catch (NumberFormatException e) {
			
		}
		System.out.println(resp);
		System.out.println(id);
	}

}
