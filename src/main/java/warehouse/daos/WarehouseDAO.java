package warehouse.daos;

import java.util.List;
import warehouse.models.WarehouseObject;

public interface WarehouseDAO {
	
	public List<WarehouseObject> findAll();
	public List<WarehouseObject> findAllOfType(String type);
	public WarehouseObject findBySlotId(int slotId);
	public WarehouseObject addToDatabase(WarehouseObject warehouseObject);
	public void update(WarehouseObject warehouseObject);
	public void delete(WarehouseObject warehouseObject);
	public void delete(int slotId);
//	public void deleteMany(int[] slotIds);
//	public void deleteMany(WarehouseObject[] warehouseObjects);
}
