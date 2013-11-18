package M1.server;

import M2.Component;
import M2.ComponentPort;

public class ServerSendRequestPort extends ComponentPort {

	private String sendBuffer;
	
	public ServerSendRequestPort(Component parent) {
		super("ServerSendRequestPort",parent);
		this.sendBuffer = new String();
	}
	
	@Override
	public void setValue(Object object) {
		this.sendBuffer = (String)object;
	}

	@Override
	public String getValue() {
		return this.sendBuffer;
	}

}
