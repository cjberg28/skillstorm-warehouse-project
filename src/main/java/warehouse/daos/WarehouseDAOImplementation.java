package warehouse.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import warehouse.config.WarehouseDatabaseCredentials;
import warehouse.models.WarehouseObject;

public class WarehouseDAOImplementation implements WarehouseDAO {

	@Override
	public List<WarehouseObject> findAll() {
		String sql = "SELECT * FROM warehouse1";
		
		try (Connection conn = WarehouseDatabaseCredentials.getInstance().getConnection()) {
			
			Statement statement = conn.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			LinkedList<WarehouseObject> warehouseObjects = new LinkedList<>();
			
			while (rs.next()) {
				WarehouseObject w = new WarehouseObject(rs.getInt("slotId"),rs.getFloat("spaceRequired"),
														rs.getString("description"),rs.getString("type"));
				warehouseObjects.add(w);
			}
			
			return warehouseObjects;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<WarehouseObject> findAllOfType(String type) {
		
		String sql = "SELECT * FROM warehouse1 WHERE type LIKE ?";
		
		try (Connection conn = WarehouseDatabaseCredentials.getInstance().getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			switch (type.toUpperCase()) {
				case "EQUIPMENT":
				case "MEDICAL":
				case "FOOD":
				case "KITCHEN":
				case "SHELTER":
				case "CLOTHING":
				case "FOOTWEAR":
				case "LEISURE":
				case "STORAGE":
				case "MISC":
					//MySQL enums are not case-sensitive, so don't have to fill the ps with uppercase string.
					ps.setString(1, type);
					break;
				default:
					throw new SQLException("Invalid WarehouseObject type selected.");
			}
			
			ResultSet rs = ps.executeQuery();
			
			LinkedList<WarehouseObject> warehouseObjects = new LinkedList<>();
			
			while (rs.next()) {
				WarehouseObject w = new WarehouseObject(rs.getInt("slotId"),rs.getFloat("spaceRequired"),
														rs.getString("description"),rs.getString("type"));
				warehouseObjects.add(w);
			}
			
			return warehouseObjects;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public WarehouseObject findBySlotId(int slotId) {
		
		String sql = "SELECT * FROM warehouse1 WHERE slotId = ?";
		
		try (Connection conn = WarehouseDatabaseCredentials.getInstance().getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, slotId);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new WarehouseObject(rs.getInt("slotId"),rs.getFloat("spaceRequired"),
										   rs.getString("description"),rs.getString("type"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public WarehouseObject addToDatabase(WarehouseObject warehouseObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(WarehouseObject warehouseObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(WarehouseObject warehouseObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int slotId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMany(int[] slotIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMany(WarehouseObject[] warehouseObjects) {
		// TODO Auto-generated method stub
		
	}

}
