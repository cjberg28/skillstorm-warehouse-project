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

	/**
	 * Finds all warehouse objects.
	 * Assumptions: Information stored in the database is legal, e.g., spaceRequired > 0.
	 * @return List of all warehouse objects, or null if failure.
	 */
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
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Finds all warehouse objects of a given type.
	 * Assumptions: Information stored in the database is legal, e.g., spaceRequired > 0.
	 * @param type (String, the type of object being searched for (KITCHEN, LEISURE, etc.))
	 * @return List of warehouse objects with the given type, or null if failure.
	 */
	@Override
	public List<WarehouseObject> findAllOfType(String type) {
		
		String sql = "SELECT * FROM warehouse1 WHERE type LIKE ?";
		
		try (Connection conn = WarehouseDatabaseCredentials.getInstance().getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			switch (type.trim().toUpperCase()) {
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
					ps.setString(1, type.trim());
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
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Finds a warehouse object with a given slotId.
	 * Assumptions: Information stored in the database is legal, e.g., spaceRequired > 0.
	 * @param slotId (int, the slotId of the object being searched for)
	 * @return Warehouse object with the given slotId, or null if failure.
	 */
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
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Adds a warehouse object to the database.
	 * Assumptions: The warehouse object should not have a slotId (it will be assigned).
	 * Giving the object a slotId will do nothing, and that slotId will actually be lost.
	 * @param warehouseObject (WarehouseObject, the object you want to add)
	 * @return The warehouse object with the new slotId, or null if failure.
	 */
	@Override
	public WarehouseObject addToDatabase(WarehouseObject warehouseObject) {
		
		//slotId not required, as it is auto-incremented.
		String sql = "INSERT INTO warehouse1(spaceRequired,description,type) VALUES(?,?,?)";
		
		try (Connection conn = WarehouseDatabaseCredentials.getInstance().getConnection()) {
			
			//Start a transaction.
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			//Warehouse object should already have validated fields.
			ps.setFloat(1, warehouseObject.getSpaceRequired());
			ps.setString(2, warehouseObject.getDescription());
			ps.setString(3, warehouseObject.getType());
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected != 0) {
				
				ResultSet keys = ps.getGeneratedKeys();
				
				if (keys.next()) {
					
					//Assign the slotId to the warehouse object.
					warehouseObject.setSlotId(keys.getInt(1));//Not edge case, MySQL returns correct keys.
					conn.commit();
					return warehouseObject;
				} else {
					System.err.println("Error: Query added a new object but did not return a key.");
					conn.rollback();
				}
				
			} else {
				System.err.println("Error: Query did not add a new object to the database.");
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Updates a warehouse object.
	 * Assumptions: The warehouse object argument has a slotId.
	 * @param warehouseObject (WarehouseObject; the object being updated, with new information)
	 */
	@Override
	public void update(WarehouseObject warehouseObject) {
		String sql = "UPDATE warehouse1 SET spaceRequired = ?, description = ?, type = ? WHERE slotId = ?";
		
		try (Connection conn = WarehouseDatabaseCredentials.getInstance().getConnection()) {
			
			if (warehouseObject.getSlotId() == 0) {//slotId not assigned
				throw new IllegalArgumentException("WarehouseObject argument does not have a slotId. Unable to update.");
			}
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setFloat(1, warehouseObject.getSpaceRequired());
			ps.setString(2, warehouseObject.getDescription());
			ps.setString(3, warehouseObject.getType());
			ps.setInt(4, warehouseObject.getSlotId());
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected != 0) {
				conn.commit();
			} else {
				System.err.println("Could not update object in database. (DAOImp.update())");
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Deletes a warehouse object.
	 * Assumptions: The warehouse object argument has a slotId.
	 * @param warehouseObject (WarehouseObject; the object being deleted)
	 */
	@Override
	public void delete(WarehouseObject warehouseObject) {
		String sql = "DELETE FROM warehouse1 WHERE slotId = ?";
		
		try (Connection conn = WarehouseDatabaseCredentials.getInstance().getConnection()) {
			
			if (warehouseObject.getSlotId() == 0) {//slotId not assigned
				throw new IllegalArgumentException("WarehouseObject argument does not have a slotId. Unable to delete.");
			}
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, warehouseObject.getSlotId());
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected != 0) {
				conn.commit();
			} else {
				System.err.println("Object failed to be deleted. (DAOImp.delete(wObj))");
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Deletes a warehouse object.
	 * Assumptions: The argument slotId > 0.
	 * @param slotId (int; the slotId of the object being deleted)
	 */
	@Override
	public void delete(int slotId) {
		String sql = "DELETE FROM warehouse1 WHERE slotId = ?";
		
		try (Connection conn = WarehouseDatabaseCredentials.getInstance().getConnection()) {
			
			if (slotId <= 0) {//slotId not assigned
				throw new IllegalArgumentException("Invalid slotId passed as argument. Unable to delete.");
			}
			
			conn.setAutoCommit(false);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, slotId);
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected != 0) {
				conn.commit();
			} else {
				System.err.println("Object failed to be deleted. (DAOImp.delete(int))");
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}

//	@Override
//	public void deleteMany(int[] slotIds) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deleteMany(WarehouseObject[] warehouseObjects) {
//		// TODO Auto-generated method stub
//		
//	} FUNCTIONALITY NOT REQUIRED

}
