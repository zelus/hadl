package M1.server.serverDetails.cmToSm;

import M2.Connector;
import M2.ConnectorRole;

public class ConnectedRole extends ConnectorRole {

	private String conName;
	
	public ConnectedRole(Connector parent) {
		super("ConnectedRole",parent);
		this.conName = new String();
	}
	
	@Override
	public void setValue(Object object) {
		this.conName = (String)object;
	}

	@Override
	public String getValue() {
		return this.conName;
	}

}
