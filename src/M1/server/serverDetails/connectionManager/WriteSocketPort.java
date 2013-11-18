package M1.server.serverDetails.connectionManager;

import M2.Component;
import M2.ComponentPort;

public class WriteSocketPort extends ComponentPort {

	private String socketBuffer;
	
	public WriteSocketPort(Component parent) {
		super("WriteSocketPort",parent);
		this.socketBuffer = new String();
	}
	
	@Override
	public void setValue(Object object) {
		this.socketBuffer = (String)object;
		System.out.println("\t[M1] WriteSocketPort updated with value \"" + socketBuffer + "\"");
	}

	@Override
	public String getValue() {
		return this.socketBuffer;
	}

}
