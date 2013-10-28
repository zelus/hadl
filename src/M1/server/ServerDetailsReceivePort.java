package M1.server;

import M2.Configuration;
import M2.ConfigurationPort;

public class ServerDetailsReceivePort extends ConfigurationPort {

	private String receiveBuffer;
	
	public ServerDetailsReceivePort(Configuration parent) {
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
