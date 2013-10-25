package M1.overview;

import M2.Component;
import M2.ComponentPort;

public class ReceiveRequestPort extends ComponentPort {

	private String receiveBuffer;
	
	public ReceiveRequestPort(Component parent) {
		super("ReceiveRequest", parent);
		receiveBuffer = new String();
	}

	@Override
	public void setValue(Object msg) {
		receiveBuffer = (String)msg;
	}

	@Override
	public String getValue() {
		return receiveBuffer;
	}
	
	

}
