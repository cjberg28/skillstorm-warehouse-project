package warehouse.config;
/**
 * This class just exists to give the maximum capacity of the warehouse.
 */
public class WarehouseSpace {
	private static final float MAX_SPACE = 10000f;
	
	public static float getMaximumSpace() {
		return WarehouseSpace.MAX_SPACE;
	}

}
