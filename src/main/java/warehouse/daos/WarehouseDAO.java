package warehouse.daos;

import java.util.List;
import warehouse.models.WarehouseObject;

public interface WarehouseDAO {
	
	public List<WarehouseObject> findAll();
	public List<WarehouseObject> findAllOfType(String type);
	public WarehouseObject findBySlotId(int slotId);
	public WarehouseObject addToDatabase(WarehouseObject warehouseObject);
	public boolean update(WarehouseObject warehouseObject);
	public boolean delete(WarehouseObject warehouseObject);
	public boolean delete(int slotId);
	public float getRemainingSpace();
//	public void deleteMany(int[] slotIds);
//	public void deleteMany(WarehouseObject[] warehouseObjects);
}
