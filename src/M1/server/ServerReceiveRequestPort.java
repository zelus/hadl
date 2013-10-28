package M1.server;

import M2.Component;
import M2.ComponentPort;

public class ServerReceiveRequestPort extends ComponentPort {

	private String receiveBuffer;
	
	public ServerReceiveRequestPort(Component parent) {
		super("ServerReceiveRequestPort", parent);
		receiveBuffer = new String();
	}

	@Override
	public void setValue(Object object) {
		receiveBuffer = (String)object;
		System.out.println("\t[M1] ServerReceiveRequestPort updated with the value \"" + receiveBuffer + "\"" );

	}

	@Override
	public String getValue() {
		return receiveBuffer;
	}

}
