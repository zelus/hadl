package M1.overview;

import M2.Component;
import M2.ComponentPort;

public class SendRequestPort extends ComponentPort {

	private String sendBuffer;
	
	public SendRequestPort(Component parent) {
		super("SendRequest", parent);
		sendBuffer = new String();
	}

	@Override
	public void setValue(Object msg) {
		sendBuffer = (String)msg;
	}

	@Override
	public String getValue() {
		return sendBuffer;
	}

}
