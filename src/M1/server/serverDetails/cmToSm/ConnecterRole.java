package M1.server.serverDetails.cmToSm;

import M2.Connector;
import M2.ConnectorRole;

public class ConnecterRole extends ConnectorRole {

	private String conName;
	
	public ConnecterRole(Connector parent) {
		super("ConnecterRole",parent);
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
