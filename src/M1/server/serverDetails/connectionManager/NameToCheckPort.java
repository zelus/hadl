package M1.server.serverDetails.connectionManager;

import M2.Component;
import M2.ComponentPort;

public class NameToCheckPort extends ComponentPort {

	private String name;
	
	public NameToCheckPort(Component parent) {
		super("NameToCheckPort",parent);
		name = new String();
	}
	
	@Override
	public void setValue(Object object) {
		this.name = (String)object;
	}

	@Override
	public String getValue() {
		return name;
	}

}
