package warehouse.models;
/**
 * This class is used to send messages back to the front end.
 * It can be any message, and will appear on a card with "Notice" on it.
 */
public class JSONResultMessage {

	private String message;
	
	public JSONResultMessage() {
		
	}
	
	public JSONResultMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
