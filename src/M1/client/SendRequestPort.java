package M1.client;

import M2.Component;
import M2.ComponentPort;

public class SendRequestPort extends ComponentPort {

	private String sendBuffer;
	
	public SendRequestPort(Component parent) {
		super("SendRequestPort", parent);
		sendBuffer = new String();
	}

	@Override
	public void setValue(Object msg) {
		sendBuffer = (String)msg;
		System.out.println("\t[M1] " + name + " updated with the value \"" + sendBuffer + "\"" );
	}

	@Override
	public String getValue() {
		return sendBuffer;
	}

}
