package warehouse;

import java.util.LinkedList;
import java.util.List;

import warehouse.daos.WarehouseDAOImplementation;
import warehouse.models.WarehouseObject;

public class Driver {

	public static void main(String[] args) {
		WarehouseObject w = new WarehouseObject(7,500.0f,"Box of Tents","SHELTER");
		
		WarehouseDAOImplementation dao = new WarehouseDAOImplementation();
		
//		dao.addToDatabase(w);

		
		List<WarehouseObject> objs = dao.findAll();
		
		
		System.out.println("findAll():");
		for (WarehouseObject o : objs) {
			System.out.println(o);
		}
		
		System.out.println("FindAllOfType():");
		System.out.println(dao.findAllOfType("MISC"));
		
		System.out.println("FindBySlotId():");
		System.out.println(dao.findBySlotId(1));
		
		System.out.println("update():");
		dao.update(w);
		System.out.println(dao.findBySlotId(3));
		
//		System.out.println("delete(wObj):");
//		dao.delete(w);
		System.out.println("delete(id):");
		dao.delete(3);
	}

}
