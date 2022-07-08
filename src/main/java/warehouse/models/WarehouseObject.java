package warehouse.models;

import java.sql.SQLException;

public class WarehouseObject {
	
	private int slotId;
	private float spaceRequired;
	private String description;
	private String type;
	
	public WarehouseObject() {
		
	}

	public WarehouseObject(int slotId, float spaceRequired, String description, String type) throws IllegalArgumentException {
		if (slotId <= 0) {
			throw new IllegalArgumentException("slotId cannot be negative or zero.");
		}
		if (spaceRequired <= 0) {
			throw new IllegalArgumentException("spaceRequired cannot be negative or zero.");
		}
		if (description.isBlank()) {
			throw new IllegalArgumentException("description cannot be empty.");
		}
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
				break;
			case "":
				throw new IllegalArgumentException("type cannot be empty.");
			default:
				throw new IllegalArgumentException("Category chosen for type does not exist.");
		}
		
		this.slotId = slotId;
		this.spaceRequired = spaceRequired;
		this.description = description;
		this.type = type.trim().toUpperCase();
	}

	//slotId is auto-incremented, so not necessary.
	public WarehouseObject(float spaceRequired, String description, String type) throws IllegalArgumentException {
		if (spaceRequired <= 0) {
			throw new IllegalArgumentException("spaceRequired cannot be negative or zero.");
		}
		if (description.isBlank()) {
			throw new IllegalArgumentException("description cannot be empty.");
		}
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
				break;
			case "":
				throw new IllegalArgumentException("type cannot be empty.");
			default:
				throw new IllegalArgumentException("Category chosen for type does not exist.");
		}
		
		this.spaceRequired = spaceRequired;
		this.description = description;
		this.type = type.trim().toUpperCase();
	}

	public int getSlotId() {
		return slotId;
	}

	public float getSpaceRequired() {
		return spaceRequired;
	}

	public String getDescription() {
		return description;
	}
	
	public String getType() {
		return type;
	}

	public void setSlotId(int slotId) {
		try {
			if (slotId <= 0) {
				throw new IllegalArgumentException("Cannot set slotId to 0 or negative. Unable to set.");
			}
			this.slotId = slotId;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void setSpaceRequired(float spaceRequired) {
		try {
			if (spaceRequired <= 0) {
				throw new IllegalArgumentException("Cannot set spaceRequired to 0 or negative. Unable to set.");
			}
			this.spaceRequired = spaceRequired;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	public void setDescription(String description) {
		try {
			if (description.isBlank()) {
				throw new IllegalArgumentException("Cannot set description to empty. Unable to set.");
			}
			this.description = description;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void setType(String type) {
		try {
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
					break;
				case "":
					throw new IllegalArgumentException("type cannot be set to empty. Unable to set.");
				default:
					throw new IllegalArgumentException("Cannot set type to nonexisting category. Unable to set.");
			}
			this.type = type.trim().toUpperCase();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "WarehouseObject [slotId=" + slotId + ", spaceRequired=" + spaceRequired + ", description=" + description
				+ ", type=" + type + "]";
	}
}
