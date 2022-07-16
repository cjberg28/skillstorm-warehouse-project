package warehouse.models;

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
