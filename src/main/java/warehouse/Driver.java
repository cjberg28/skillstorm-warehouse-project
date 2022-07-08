package warehouse;

import warehouse.models.WarehouseObject;

public class Driver {

	public static void main(String[] args) {
		WarehouseObject w = new WarehouseObject(12.5f,"Memes","KITCHEN");
		
		System.out.println(w.getSlotId());//Default 0

	}

}
