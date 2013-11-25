package M1.client;

import M2.Component;
import M2.ComponentPort;

public class ReceiveRequestPort extends ComponentPort {

	private String receiveBuffer;
	
	public ReceiveRequestPort(Component parent) {
		super("ReceiveRequestPort", parent);
		receiveBuffer = new String();
	}

	@Override
	public void setValue(Object msg) {
		receiveBuffer = (String)msg;
		System.out.println("\t[M1] " + name + " updated with the value \"" + receiveBuffer + "\"" );
	}

	@Override
	public String getValue() {
		return receiveBuffer;
	}
	
	

}
