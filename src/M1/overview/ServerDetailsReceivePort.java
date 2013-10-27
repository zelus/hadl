package M1.overview;

import M2.Component;
import M2.ComponentPort;

public class ServerDetailsReceivePort extends ComponentPort {

	private String receiveBuffer;
	
	public ServerDetailsReceivePort(Component parent) {
		super("ServerDetailsReceivePort",parent);
		this.receiveBuffer = new String();
	}
	
	@Override
	public void setValue(Object object) {
		this.receiveBuffer = (String)object;
		System.out.println("\t[M1] " + name + " updated with value \"" + receiveBuffer + "\"");
	}

	@Override
	public String getValue() {
		return this.receiveBuffer;
	}

}
