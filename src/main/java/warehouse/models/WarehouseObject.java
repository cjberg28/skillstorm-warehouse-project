package warehouse.models;

public class WarehouseObject {
	
	private int slotId;
	private float spaceRequired;
	private String description;
	private String type;
	
	public WarehouseObject() {
		
	}

	public WarehouseObject(int slotId, float spaceRequired, String description, String type) {
		this.slotId = slotId;
		this.spaceRequired = spaceRequired;
		this.description = description;
		this.type = type;
	}

	//slotId is auto-incremented, so not necessary.
	public WarehouseObject(float spaceRequired, String description, String type) {
		this.spaceRequired = spaceRequired;
		this.description = description;
		this.type = type;
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
		this.slotId = slotId;
	}

	public void setSpaceRequired(float spaceRequired) {
		this.spaceRequired = spaceRequired;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "WarehouseObject [slotId=" + slotId + ", spaceRequired=" + spaceRequired + ", description=" + description
				+ ", type=" + type + "]";
	}
}
