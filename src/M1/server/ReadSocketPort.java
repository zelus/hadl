package M1.server;

import M2.Component;
import M2.ComponentPort;

public class ReadSocketPort extends ComponentPort {

	private String socketBuffer;
	
	public ReadSocketPort(Component parent) {
		super("ReadSocketPort", parent);
		this.socketBuffer = new String();
	}

	@Override
	public void setValue(Object object) {
		this.socketBuffer = (String)object;
	}

	@Override
	public String getValue() {
		return this.socketBuffer;
	}

}
