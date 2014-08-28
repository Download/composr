package reflect;

public class TextMessage implements Message {
	private final String msg;
	
	public TextMessage(String message) {
		this.msg = message;
	}
	
	@Override public String message() {
		return msg;
	}
}
